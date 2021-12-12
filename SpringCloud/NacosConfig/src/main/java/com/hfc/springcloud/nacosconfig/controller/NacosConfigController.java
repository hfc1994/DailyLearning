package com.hfc.springcloud.nacosconfig.controller;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import com.alibaba.nacos.api.exception.NacosException;
import com.hfc.springcloud.nacosconfig.config.Location;
import com.hfc.springcloud.nacosconfig.config.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Created by hfc on 2021/4/28.
 *
 * 缺少@RefreshScope的情况下，应用启动时会获取最新的配置，但运行时不会接受配置更新
 */
@RefreshScope
@RestController
@RequestMapping("/testnacos")
public class NacosConfigController {

    @Autowired
    private Person person;

    @Autowired
    private Location location;

    @Autowired
    private NacosConfigManager nacosManager;

    @Value("${spring.application.name}")
    private String prefix;
    @Value("${spring.cloud.nacos.config.file-extension}")
    private String suffix;
    @Value("${spring.cloud.nacos.config.group}")
    private String group;

    private String fullDataId;

    @PostConstruct
    public void registerListener() throws NacosException {
        this.fullDataId = prefix + "." + suffix;
        // 只监听了一个配置
        nacosManager.getConfigService().addListener(fullDataId, group, new AbstractListener() {
            @Override
            public void receiveConfigInfo(String content) {
                // FIXME: 2021/5/7 出现被触发3次的情况，一次ClientWorker两次CacheData，原因暂不明
                // 可能是有两个变量所以在CacheData中触发两次
                // ClientWorker时数据未更新完成，CacheData时数据更新完成
                System.out.println("******");
                // content的内容是实时的
                System.out.println(content);
                // Person的内容暂时是过时的
                System.out.println("person: " + person.getAge());

                Thread t = new Thread(() -> {
                    try {
                        // Spring容器中的配置bean不会立即更新，需要等待。1秒和2秒均没效果
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("*-*-* after 3 second -- person: " + person.getAge() + " *-*-*");
                });
                t.start();
                System.out.println("******");
            }
        });

        // 可以监听原先并不存在的配置文件
        nacosManager.getConfigService().addListener("check.properties", group, new AbstractListener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("--- check.properties ---");
                System.out.println(configInfo);
                System.out.println("--- check.properties ---");
            }
        });
    }

    @GetMapping("/query")
    public String showAge() {
        String ret1 = "person = " + person.getName() + ":" + person.getAge();
        String ret2 = ";location = " + location.getName() + ":" + location.getNumber();
        System.out.println(ret1 + ret2);

        return ret1 + ret2;
    }

    @GetMapping("/update")
    public boolean updateAge() throws NacosException {
        JSONObject json = new JSONObject();
        json.put("person.age", 23);
        json.put("person.name", "lisi");

        return nacosManager.getConfigService().publishConfig(fullDataId, group,
                json.toJSONString(), ConfigType.JSON.getType());
    }

    // 可以向原先不存在的配置文件中增加配置信息
    @GetMapping("/addNotExist")
    public boolean addNotExist() throws NacosException {
        JSONObject json = new JSONObject();
        json.put("checkId", 123);
        json.put("checkTime", "2021");

        return nacosManager.getConfigService().publishConfig("check.properties", group,
                json.toJSONString(), ConfigType.JSON.getType());
    }
}

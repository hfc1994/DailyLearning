package com.hfc.dubbo.provider.service;

import org.apache.dubbo.config.annotation.DubboService;
import com.hfc.dubbo.provider.service.ifc.InfoService;
import org.springframework.stereotype.Component;

/**
 * Created by hfc on 2021/3/6.
 */
// dubbo的Service注解，用于暴露服务。
// version版本号，可以给服务设置不同的版本号来实现服务的多版本灰度发布。
//
// loadbalance是负载均衡，dubbo提供了4种策略，配置时使用全小写：
//  - Random：按权重的随机负载均衡，dubbo的默认策略。
//  - RoundRobin：按权重的轮询负载均衡，即在轮询的基础上添加了权重的策略。
//  - LeastActive：最少活跃调用数，相同活跃数的随机访问。活跃数是指调用前后的计数差值即响应时间的长短，这种策略可以使响应慢的提供者收到的请求较少。
//  - ConsistentHash：一致性哈希，相同参数的请求总是发到同一提供者。
//
// cluster是集群容错，dubbo提供了6种策略，配置时使用全小写：
//  - Failover：失败自动切换，当出现失败，重试其它服务器。通常用于读操作，但重试会带来更长延迟。dubbo的默认策略，可搭配客户端的retries来使用。
//  - Failfast：快速失败，只发起一次调用，失败立即报错。通常用于非幂等的写操作。
//  - Failsafe：安全失败，出现异常时直接忽略。通常用于写入审计日志等操作。
//  - Failback：失败自动恢复，自动记录失败请求，定时重发。通常用于消息通知操作。
//  - Forking：并行调用多个服务器，只要一个成功即返回。通常用于实时性要求较高的读操作，但需要浪费更多服务资源。可通过forks来设置最大并行数。
//  - Broadcast：广播调用所有提供者，逐个调用，任意一个报错则报错。通常用于通知所有提供者更新缓存或日志等本地资源。
//
// dubbo推荐在Provider上尽量多配置Consumer端的属性，原因是：
//  - 服务端比消费端更清除服务性能参数等
//  - 服务端配置是可控的，Consumer不配置则会使用Provider的配置值，即Provider的配置是Consumer的缺省值。
// 配置优先级：方法级优先，接口级次之，全局配置最后；如果级别一样，则消费端优先，提供端次之。
@DubboService(version = "1.0.0", loadbalance = "roundrobin", cluster = "failfast")
@Component
public class InfoServiceImpl implements InfoService {

    @Override
    public String getInfo() {
        return "there is some information";
    }

}

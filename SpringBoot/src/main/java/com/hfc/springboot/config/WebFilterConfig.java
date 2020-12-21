package com.hfc.springboot.config;

import com.hfc.springboot.filters.TestFilter3;
import com.hfc.springboot.filters.TestFilter4;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * Created by hfc on 2020/12/21.
 *
 * 当存在多个filter的时候通过order来设置调用顺序
 */
@Configuration
public class WebFilterConfig {

    @Bean
    public FilterRegistrationBean regFilter1() {
        TestFilter3 filter3 = new TestFilter3();

        FilterRegistrationBean<Filter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(filter3);
        filterBean.addUrlPatterns("/test/*");
//        filterBean.addInitParameter("", "");    // 设置初始参数
        filterBean.setName("filter3");
        filterBean.setOrder(2); // 执行顺序

        return filterBean;
    }

    @Bean
    public FilterRegistrationBean regFilter2() {
        TestFilter4 filter4 = new TestFilter4();

        FilterRegistrationBean<Filter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(filter4);
        filterBean.addUrlPatterns("/test/*");
        filterBean.setName("filter4");
        filterBean.setOrder(1); // 执行顺序

        return filterBean;
    }
}

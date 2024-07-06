package com.hfc.springboot.config;

import com.hfc.springboot.handlers.JwtTokenResolver;
import com.hfc.springboot.interceptors.TestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * Created by hfc on 2020/8/25.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private JwtTokenResolver jwtTokenResolver;

    // 静态资源，自定义静态资源映射目录
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/web/**")
                .addResourceLocations("file:web/");
    }

    // 拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TestInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/*/ds", "/web/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(jwtTokenResolver);
    }

    // 信息转换器
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 避免 controller 返回 String 类型时出现“xxx.CommonResult cannot be cast to java.lang.String 错误”
        converters.removeIf(cvt -> cvt instanceof StringHttpMessageConverter);
    }

//    // 页面跳转
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//    }

//    // 视图解析器
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//    }

}

package com.hfc.springboot.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hfc on 2020/12/21.
 *
 * WebFilter注解是Servlet3.0的规范，并没有指定执行的顺序，其执行顺序依赖于Filter的<b>类名</b>
 */
@WebFilter(urlPatterns = "/test/*")
public class TestFilter2 implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("--- TestFilter2 init ---");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        System.out.println("--- TestFilter2 before ---");
        System.out.println(req.getRequestURI());
        chain.doFilter(req, resp);
        System.out.println("--- TestFilter2 after ---");
        System.out.println(resp.getStatus());
    }

    @Override
    public void destroy() {
        System.out.println("--- TestFilter2 destroy ---");
    }
}

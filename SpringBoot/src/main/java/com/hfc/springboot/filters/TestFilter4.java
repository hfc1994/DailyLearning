package com.hfc.springboot.filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by hfc on 2020/12/21.
 */
public class TestFilter4 implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        System.out.println("--- TestFilter4 ---");
        chain.doFilter(request, response);
    }

}

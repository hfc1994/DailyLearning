package com.hfc.springboot.filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by hfc on 2020/12/21.
 */
public class TestFilter3 implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        System.out.println("--- TestFilter3 ---");
        chain.doFilter(request, response);
    }

}

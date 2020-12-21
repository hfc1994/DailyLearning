package com.hfc.springboot.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hfc on 2020/12/20.
 */
@Component
public class TestFilter1 extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().contains("/test")) {
            System.out.println("--- TestFilter1 before ---");
            System.out.println(request.getRequestURI());
            filterChain.doFilter(request, response);
            System.out.println("--- TestFilter1 after ---");
            System.out.println(response.getStatus());
        } else {
            filterChain.doFilter(request, response);
        }
    }

}

package com.common.framework.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  参数过滤器
 */

public class ParamsFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        ParameterRequestWrapper parmsRequest = new ParameterRequestWrapper(httpServletRequest);
        filterChain.doFilter(parmsRequest, httpServletResponse);
    }

}

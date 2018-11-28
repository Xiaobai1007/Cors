package com.xiaobai.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "MyFilter")
public class MyFilter implements Filter {

    public void destroy() {
    }
    String allowList [] = null;

    @Override
    public void init(FilterConfig config) throws ServletException {
        String origins = config.getInitParameter("allowList");
        if(origins != null){
            if(origins.equals("*")){
                allowList = new String[]{"*"};
            }else {
                allowList = origins.split(",");
            }
        }
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String origin = request.getHeader("Origin");
        if (origin != null && !origin.isEmpty()) {
            for (String s : allowList) {
                if (s.equals(origin) || s.equals("*")) {
                    response.setHeader("Access-Control-Allow-Origin", origin);
                }
            }
        }
        chain.doFilter(request, response);
    }
}

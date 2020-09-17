package com.example.demo.security.filter;

import javax.servlet.*;
import java.io.IOException;

//@Component
public class MyNewFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("The new filter is called..");

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public String toString() {
        return "MyNewFilter{}";
    }
    public MyNewFilter() {
        System.out.println(toString());
    }
}

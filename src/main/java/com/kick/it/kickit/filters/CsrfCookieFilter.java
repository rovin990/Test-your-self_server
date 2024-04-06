package com.kick.it.kickit.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CsrfCookieFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        System.out.println(csrfToken.getToken());
//        csrfToken.getToken();
        if(csrfToken!=null){
            response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
        }
        System.out.println("CSRF filter");
        filterChain.doFilter(request,response);
    }

//    CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
//    // Render the token value to a cookie by causing the deferred token to be loaded
//		csrfToken.getToken();
}

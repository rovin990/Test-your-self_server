package com.kick.it.kickit.filters;

import com.kick.it.kickit.Utility.Security_Constant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth!=null){
            SecretKey key = Keys.hmacShaKeyFor(Security_Constant.JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            String jwt= Jwts.builder()
                    .setIssuer("Quiz portal")
                    .setSubject("JWT Token")
                    .claim("username",auth.getName())
                    .claim("authorities",populateAuthorities(auth.getAuthorities()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime()+86400000)) //86400000
                    .signWith(key)
                    .compact();
            response.setHeader(Security_Constant.JWT_HEADER,jwt);
        }

        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        System.out.println("!request.getServletPath().equals(\"/user\");"+!request.getServletPath().equals("/user"));

        return !request.getServletPath().equals("/user");
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities){

        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }
}

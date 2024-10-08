package com.kick.it.kickit.filters;

import com.kick.it.kickit.Utility.Security_Constant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader(Security_Constant.JWT_HEADER).split(" ")[1].trim();

        System.out.println("Jwt token in validator "+jwt);
        if (null != jwt) {
            try {
                SecretKey key = Keys.hmacShaKeyFor(
                        Security_Constant.JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));

                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();
                String username = String.valueOf(claims.get("username"));
                String authorities = (String) claims.get("authorities");
                Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                System.out.println(e.getMessage());



                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setHeader(Security_Constant.JWT_TOKEN_EXPIRE,"Invalid Token");
                response.getWriter().write("Invalid Token");
                return;
//                throw new InvalidTokenException("Invalid Token received!");
            }

        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        if(request.getServletPath().equals("/register"))return true;
        return request.getServletPath().equals("/user");
    }

    private void handleInvalidCorrelationId(HttpServletResponse response) throws IOException {
    }
}

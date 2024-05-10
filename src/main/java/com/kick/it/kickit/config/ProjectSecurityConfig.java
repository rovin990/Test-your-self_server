package com.kick.it.kickit.config;

import com.kick.it.kickit.filters.CsrfCookieFilter;
import com.kick.it.kickit.filters.JWTTokenGeneratorFilter;
import com.kick.it.kickit.filters.JWTTokenValidatorFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.*;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Supplier;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    @Autowired
    @Qualifier("delegatedAuthenticationEntryPoint")
    DelegatedAuthenticationEntryPoint authEntryPoint;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        CsrfTokenRequestAttributeHandler requestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        requestAttributeHandler.setCsrfRequestAttributeName("_csrf");
        HttpServletRequest httpServletRequest ;

        http.securityContext(securityContextConfigurer -> securityContextConfigurer.requireExplicitSave(false))
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .cors(cors->cors.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("https://quizmaster.kickthepast.com,https://examsphere.kickthepast.com,http://localhost:3000,http://quizmaster-s3.s3-website-us-east-1.amazonaws.com"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))
                .csrf(csrf->csrf.disable())
//                .csrf(csrf-> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                                .csrfTokenRequestHandler(requestAttributeHandler).ignoringRequestMatchers("/register")
//                        )
//                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests( //more specific url must come first
                        (requests) -> requests
                                .requestMatchers("/question/import","/active/user","/category/active/user/**").authenticated()
                                .requestMatchers("/quiz/active","quiz/category/active/**","/image/options","/image/category","/image/question","/payment/orders","/payment/success").authenticated()
                                .requestMatchers("/category","/question","/quiz","/user","/image","/test-response").authenticated()
                                .requestMatchers("/image/**","/category/**","/quiz/**","/question/**","/test-response/**").authenticated()
                                .requestMatchers("/register","/contact-us").permitAll())
                .formLogin(withDefaults())
                .httpBasic(basic-> basic.authenticationEntryPoint(authEntryPoint))
                .exceptionHandling(Customizer.withDefaults());

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}

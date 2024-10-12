package com.backend.rest.webservices.restfulwebservices.todo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
//Basic authentication is base64 and can easily be deocded, no expiration time etc, thats why we use JWT
//@Configuration commenting bcoz we want to use jwt
public class BasicAuthenticationSecurityConfiguration {
    //Filter chain
    //spring security authenticate all request by default
    //disableing csrf
    //stateless rest api

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(
               // auth-> auth.anyRequest().authenticated() // it will validate all request
                auth ->
                        auth.requestMatchers(new AntPathRequestMatcher("/**"))
                                .permitAll().anyRequest().authenticated()
        )
        .httpBasic(Customizer.withDefaults())
        .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .csrf().disable().build();
    }
}

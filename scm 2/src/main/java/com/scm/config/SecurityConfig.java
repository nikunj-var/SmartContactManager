package com.scm.config;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;


import com.scm.service.impl.SecurityCustomUserDetailService;



@Configuration
public class SecurityConfig{


    @Autowired private SecurityCustomUserDetailService userDetailService;

    @Autowired private OAuthAuthenticationSuccessHandler oAuthAuthenticationSuccessHandler;

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.csrf(csrf->csrf.disable()).authorizeHttpRequests(authorize->authorize.requestMatchers("/api/auth/login","/api/do-register","/login**","/api/auth/logout","/oauth2/**").permitAll().anyRequest().authenticated());
       

        
        httpSecurity.cors(cors->cors.configurationSource(request->{
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3001","http://localhost:8080"));
            corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            corsConfiguration.setAllowCredentials(true);
            corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
            corsConfiguration.setExposedHeaders(Arrays.asList("Authorization"));
            return corsConfiguration;
        }));

        // oauth configuration
        httpSecurity.oauth2Login(oauth2 -> oauth2.loginPage("/login").successHandler(oAuthAuthenticationSuccessHandler));
    
        return httpSecurity.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
     @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
   
}

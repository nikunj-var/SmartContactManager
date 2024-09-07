package com.scm.config;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import com.scm.service.impl.SecurityCustomUserDetailService;
import com.scm.service.impl.TokenService;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


@Configuration
public class SecurityConfig{


    @Autowired private SecurityCustomUserDetailService userDetailService;

    @Autowired private OAuthAuthenticationSuccessHandler oAuthAuthenticationSuccessHandler;

    @Autowired private TokenService tokenService;

    @Bean
    public AuthenticationProvider authenticationProvider(){
        System.out.println("\nAuthenticationProvider");
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,AuthenticationManager authenticationManager) throws Exception{
        System.out.println("\n securityFilterChain");
        httpSecurity.cors(cors->cors.configurationSource(request->{
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3001","http://localhost:8080"));
            corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            corsConfiguration.setAllowCredentials(true);
            corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
            corsConfiguration.setExposedHeaders(Arrays.asList("Authorization"));
            return corsConfiguration;
        }));

        httpSecurity.csrf(csrf->csrf.disable()).authorizeHttpRequests(authorize->authorize.requestMatchers("/api/auth/loginuser","/api/do-register","/login","/api/auth/logout","/oauth2/**").permitAll().requestMatchers(HttpMethod.GET,"/user/profile").hasRole("USER").anyRequest().authenticated()).sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
    .oauth2Login(oauth2 -> oauth2.loginPage("/login")
        .successHandler(oAuthAuthenticationSuccessHandler))
    .oauth2ResourceServer(oauth2 -> oauth2
        .jwt(jwt -> jwt
            .jwtAuthenticationConverter(jwtAuthenticationConverter())
            .decoder(jwtDecoder())));
    
        return httpSecurity.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        System.out.println("\n PasswordEncoder");
        return new BCryptPasswordEncoder();
    }

     @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        System.out.println("\n AuthenticationManager");
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtDecoder jwtDecoder(){      
        System.out.println("\n JwtDecoder");
        String base64SecretKey = "wHZeY2oTGViObTdxbFZyU2NmZ1lxNVlRYXhTc3hHdVA="; 
        byte[] keyBytes = Base64.getDecoder().decode(base64SecretKey);
        SecretKey secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");   
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){ 
        System.out.println("\n JwtAuthenticationConverter");
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

   
    @Bean
    
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception{        
        System.out.println("\nJwtAuthenticationFilter");
        return new JwtAuthenticationFilter(tokenService);
    }
   
}


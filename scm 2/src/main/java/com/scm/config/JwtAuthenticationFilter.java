package com.scm.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



// public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
    
//     private final String secretKey = "bmlrdW5qMTIz";
//     byte[] keyBytes = Base64.getDecoder().decode(secretKey);
//         SecretKey key = new SecretKeySpec(keyBytes, "HmacSHA256");

//      private final AuthenticationManager authenticationManager;

//     public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
//         this.authenticationManager = authenticationManager;
//     }

//     @Override
//     public Authentication attemptAuthentication(HttpServletRequest request,HttpServletResponse response){
//         String username = request.getParameter("username");
//         String password = request.getParameter("password");
//         UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
//         return authenticationManager.authenticate(authRequest);
//     }
//     @Override
//     protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
//                                             Authentication authResult) throws IOException, ServletException {
//         // Add custom logic after successful authentication if needed
//         super.successfulAuthentication(request, response, chain, authResult);
//     }

//     @Override
//     protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                               AuthenticationException failed) throws IOException, ServletException {
//         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//         response.getWriter().write("Unauthorized: Invalid token");
//     }
// }



public class JwtAuthenticationFilter extends OncePerRequestFilter {



    String base64SecretKey = "bmlrdW5qMTIg"; // Base64 encoded key

        
    // Decode Base64 key
    byte[] keyBytes = Base64.getDecoder().decode(base64SecretKey);
    SecretKey secretKey = new SecretKeySpec(keyBytes, "HmacSHA256"); // Base64 encoded key (ensure it matches with SecurityConfig)

    private final AuthenticationManager authenticationManager;  private static final String ROLE_USER = "USER";

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        System.out.println("\n\n JwtAuthenticationFilter constructor called\n\n");

        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
                
           
                System.out.println("\n\n doFilterInternal called\n\n");

        String token = request.getHeader("Authorization");

      
        if (token != null && token.startsWith("Bearer ")) {
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(secretKey)  // Decode and use the correct key
                        .parseClaimsJws(token.substring(7))
                        .getBody();
                        
                        System.out.println("claims "+claims);
                String username = claims.getSubject();

                List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>)claims.get("roles",List.class).stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

                Date expirationDate = claims.getExpiration();
                System.out.println("Token Expiration Date: " + expirationDate);

                if (username != null) {
                    Authentication auth = new UsernamePasswordAuthenticationToken(username, null,authorities);
                   
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    System.out.println("Authentication set in SecurityContextHolder: " + SecurityContextHolder.getContext().getAuthentication());
                }
                // response.setStatus(HttpServletResponse.SC_OK);
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                System.out.println("\n\ncatch called\n\n");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized: Invalid token");
                System.out.println("\n\n\nToken parsing failed: " + e.getMessage());
                return;
            }
        }

          System.out.println("\n\nRequest URL: " + request.getRequestURL());

          
          chain.doFilter(request, response);
   
          System.out.println("\n\nResponse Status: " + response.getStatus());
    }
}
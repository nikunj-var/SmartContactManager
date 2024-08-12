package com.scm.service.impl;

import java.util.*;
import java.util.stream.Collectors;
import java.security.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
    private final String jwtSecret = "wHZeY2oTGViObTdxbFZyU2NmZ1lxNVlRYXhTc3hHdVA=";
    byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
    SecretKey secretKey = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());

   
    public String generateToken(Authentication authentication){
   
        String username = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime()+4*86400000);

        List<String> roles = authentication.getAuthorities().stream()
        .map(authority -> authority.getAuthority())
        .collect(Collectors.toList());
        
        if (!roles.contains("ROLE_USER")) {
            roles.add("USER");
        }

        
        return Jwts.builder().setSubject(username).claim("roles", roles).setIssuedAt(new Date()).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS256, secretKey).compact();

    }
}
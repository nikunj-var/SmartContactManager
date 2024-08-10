package com.scm.service.impl;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.security.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
    private final String jwtSecret = "bmlrdW5qMTIg";
     byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        SecretKey secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
   
    public String generateToken(Authentication authentication){
   
        String username = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime()+86400000);

        return Jwts.builder().setSubject(username).claim("roles", Collections.singletonList("USER")).setIssuedAt(new Date()).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, secretKey).compact();

    }
}

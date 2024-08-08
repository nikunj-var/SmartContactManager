package com.scm.service.impl;

import java.util.Base64;
import java.util.Date;
import java.security.*;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
    private final String jwtSecret = "nikunj123";
    private final Key key = new SecretKeySpec(jwtSecret.getBytes(), SignatureAlgorithm.HS512.getJcaName());
    public String generateToken(Authentication authentication){
       
        System.out.println("token service called");
        String username = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime()+86400000);

        return Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, key).compact();

    }
}

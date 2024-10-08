package com.scm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.scm.helpers.AuthResponse;

import com.scm.service.impl.SecurityCustomUserDetailService;
import com.scm.service.impl.TokenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:3001")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private TokenService tokenService;
    @Autowired private SecurityCustomUserDetailService userDetailService;

    @PostMapping("/loginuser")
    public ResponseEntity<?> login(@RequestBody Map<String,String> credentials) {
        String email = credentials.get("username");
        String password = credentials.get("password");
        try{
           
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
              SecurityContextHolder.getContext().setAuthentication(authentication);
             
              String token = tokenService.generateToken(authentication);
              
              return ResponseEntity.ok(new AuthResponse(token));
        }
        catch(AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if(authentication != null){
        SecurityContextHolder.getContext().setAuthentication(null);
        httpServletRequest.getSession().invalidate();
      }
      return ResponseEntity.ok("You have been Logout Successfully!");
    }
    
}

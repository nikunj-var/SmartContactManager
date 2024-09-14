package com.scm.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.entities.User;
import com.scm.helpers.Helper;
import com.scm.service.UserService;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@CrossOrigin("http://localhost:3001")
@RequestMapping("/user")
public class UserController {

    @Autowired UserService userService;
   
    @GetMapping("/profile")
    public ResponseEntity<Map<String, String>> userProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


    String username = Helper.getEmailOfLoggedInUser(authentication);
    User user = userService.getUserByEmail(username);

    if (user != null) {
        Map<String, String> response = new HashMap<>();
        response.put("username", user.getName());
        response.put("email", user.getEmail());
        return ResponseEntity.ok(response);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    }
}

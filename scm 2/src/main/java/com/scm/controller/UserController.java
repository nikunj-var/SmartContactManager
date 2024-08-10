package com.scm.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    // user dashboard
    @RequestMapping(value = "/dashboard", method=RequestMethod.GET)
    public String userDashBoard() {
        return new String();
    }

    
    @GetMapping("/profile")
    public String userProfile(@AuthenticationPrincipal Authentication authentication) {

        System.out.println("\n\n\n\n\n\nprofile called");
        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(username);

        return user.getName();

    }
}

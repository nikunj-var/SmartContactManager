package com.scm.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.entities.User;
import com.scm.helpers.Helper;
import com.scm.service.UserService;

import org.springframework.web.bind.annotation.RequestMethod;
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
    
    @RequestMapping(value = "/profile",method = RequestMethod.GET)
    public String userProfile(Principal principal) {
        System.out.println();
        System.out.println("authenticaiton="+principal);
        System.out.println();
        // String username = Helper.getEmailOfLoggedInUser(authentication);
        // if (authentication == null || !authentication.isAuthenticated()) {
        //     // Handle the case where authentication is null or not authenticated
        //     throw new IllegalStateException("User is not authenticated");
        // }
        // User user = userService.getUserByEmail(username);
        // System.out.println();
        // System.out.println(user.getName());
        // System.out.println();
        // return user.getName();
        return "";
    }
    

    // user add contact page

    // user view contact
}

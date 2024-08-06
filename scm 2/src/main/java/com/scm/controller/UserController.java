package com.scm.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.helpers.Helper;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/user")
public class UserController {
    // user dashboard
    @RequestMapping(value = "/dashboard", method=RequestMethod.GET)
    public String userDashBoard() {
        return new String();
    }
    
    @RequestMapping(value = "/profile")
    public String userProfile(Principal principal) {
        String username = Helper.getEmailOfLoggedInUser(principal);
        return "";
    }
    

    // user add contact page

    // user view contact
}

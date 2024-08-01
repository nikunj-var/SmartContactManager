package com.scm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    

    // user add contact page

    // user view contact
}

package com.scm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scm.forms.UserForm;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api")
public class PageController {
    
    @RequestMapping("home")
    public String home(Model model){
        model.addAttribute("name", "Nikunj");
        model.addAttribute("Love", "Dev");
        return "home";
    }

    @RequestMapping("about")
    public String aboutPage(){
       return "about";
    }


    @RequestMapping("services")
    public String servicesPage(){
       return "services";
    }

    @RequestMapping("register")
    public String register(){
        return "services";
     }

   //   registration
   @PostMapping("/do-register")
     public String processRegsister(@RequestBody UserForm userForm){
      
        System.out.println(userForm);
        return "";
     }
 
}

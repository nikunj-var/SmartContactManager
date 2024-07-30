package com.scm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.service.UserService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api")
public class PageController {

   @Autowired UserService userService; 

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
      
      User user = User.builder().name(userForm.getName()).email(userForm.getEmail()).password(userForm.getPassword()).about(userForm.getAbout()).phoneNumber(userForm.getPhoneNumber()).profilePic("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFVHR62PqqslJrmbNHhwiH3Cmb99-h10mi6g&s").build();
      User savedUser = userService.saveUser(user);
        System.out.println(savedUser);
        return "";
     }
 
}

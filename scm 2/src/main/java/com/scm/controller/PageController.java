package com.scm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin("http://localhost:3001")
@RequestMapping("/api")
public class PageController {

   @Autowired UserService userService; 

   @Autowired private AuthenticationManager authenticationManager;

   @PostMapping("/do-register")
     public ResponseEntity<?> processRegsister(@Valid @RequestBody UserForm userForm,BindingResult bindingResult){
    
      if(bindingResult.hasErrors()){
         return ResponseEntity.badRequest().body("Validation errors");
      }
      User user = User.builder().name(userForm.getName()).email(userForm.getEmail()).password(userForm.getPassword()).about(userForm.getAbout()).phoneNumber(userForm.getPhoneNumber()).profilePic("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFVHR62PqqslJrmbNHhwiH3Cmb99-h10mi6g&s").build();
      User savedUser = userService.saveUser(user);
      return ResponseEntity.ok("User Registered Succesffully");
     }

   
}

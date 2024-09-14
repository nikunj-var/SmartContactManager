package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.helpers.Helper;
import com.scm.service.ContactService;
import com.scm.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin("http://localhost:3001")
@RequestMapping("/contact")
public class ContactController {

    @Autowired private ContactService contactService;
    
    @Autowired private UserService userService;

    @PostMapping("/save-contact")
    public ResponseEntity<?> saveContact(@Valid ContactForm contactForm,BindingResult bindingResult,Authentication authentication,@RequestParam("file") MultipartFile picture) {
       
        if(bindingResult.hasErrors()){
            ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Some required fields are empty.");
        }
        try{
            String username = Helper.getEmailOfLoggedInUser(authentication);

            // String fileURL = imageService.uploadImage(picture);

            if (picture != null && !picture.isEmpty()) {
                String filename = picture.getOriginalFilename();
                System.out.println(filename);
            }
            User user= userService.getUserByEmail(username);
            Contact contact = new Contact();
            contact.setName(contactForm.getName());
            contact.setDescription(contactForm.getDescription());
            contact.setAddress(contactForm.getAddress());
            contact.setEmail(contactForm.getEmail());
            // contact.setFavorite(contactForm.getFavourite());
            contact.setPhoneNumber(contactForm.getPhoneNo());
            contact.setUser(user);
            contact.setLinkedInLink(contactForm.getLinkedIn());
            contact.setWebsiteLink(contactForm.getWebsiteLink());
            // contact.setPicture(picture);
            contactService.save(contact);
            return ResponseEntity.ok("Contact Added");
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Contact Not Added");
        }
        
       
    }
    
}

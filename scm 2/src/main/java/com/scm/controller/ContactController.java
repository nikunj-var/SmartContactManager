package com.scm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import com.scm.service.ImageService;
import com.scm.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@CrossOrigin("http://localhost:3001")
@RequestMapping("/contact")
public class ContactController {

    @Autowired private ContactService contactService;
    @Autowired private ImageService imageService;
    @Autowired private UserService userService;

    @PostMapping("/save-contact")
    public ResponseEntity<?> saveContact(@Valid ContactForm contactForm,BindingResult bindingResult,Authentication authentication,@RequestParam("file") MultipartFile picture) {
       
        if(bindingResult.hasErrors()){
            ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Some required fields are empty.");
        }
        try{
            String username = Helper.getEmailOfLoggedInUser(authentication);
            String fileName = UUID.randomUUID().toString();

            String fileURL = imageService.uploadImage(picture,fileName);

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
            contact.setPicture(fileURL);
            contact.setCloudinaryImagePublicId(fileName);
            contactService.save(contact);
            return ResponseEntity.ok("Contact Added");
        }
        catch(Exception e){
            System.out.println("\n\n\n\n\nerror"+e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Contact Not Added");
        }
        
       
    }
    

    @GetMapping("/getAll")
    public ResponseEntity<ArrayList<Contact>> getAllContacts(Authentication authentication) {
        System.out.println("\n\ngetadll called\n\n");
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        List<Contact> contacts = contactService.getByUser(user);

        ArrayList<Contact> contactArrayList = new ArrayList<>(contacts);
        // Check if the contact list is empty and return appropriate status
        if (contacts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // Returning the contact list wrapped in a ResponseEntity with HTTP 200 status
        return new ResponseEntity<>(contactArrayList, HttpStatus.OK);
    }
    
}

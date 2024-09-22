package com.scm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ResponseEntity<?> getAllContacts(Authentication authentication,@RequestParam(value = "page",defaultValue = "0") int page,@RequestParam(value =  "size",defaultValue = "10") int size,@RequestParam(value = "sortBy",defaultValue = "name") String sortBy, @RequestParam(value = "direction",defaultValue = "asc") String direction) {
       
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
       
        Page<Contact> contacts =  contactService.getByUser(user,page,size,sortBy,direction);

        if (contacts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
      
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchContact(@RequestParam("q") String query,@RequestParam(value = "page",defaultValue = "0") int page,@RequestParam(value =  "size",defaultValue = "10") int size,@RequestParam(value = "sortBy",defaultValue = "name") String sortBy, @RequestParam(value = "direction",defaultValue = "asc") String direction) {

    Page<Contact> contacts = contactService.searchByName(query, size, page, sortBy, direction);
    
    if (contacts.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  
    return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteContact(@RequestParam(value = "id") String id) {
    try {

        System.out.println("\n\ndelete called"+id);
        contactService.delete(id); // Ensure that the delete method works correctly
        return ResponseEntity.ok("Contact deleted successfully");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Error deleting contact: " + e.getMessage());
    }
}
    
    
}

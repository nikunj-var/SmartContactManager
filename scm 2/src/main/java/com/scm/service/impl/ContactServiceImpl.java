package com.scm.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositories.ContactRepo;
import com.scm.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired private ContactRepo contactRepo;
    @Override
    public Contact save(Contact contact) {
        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepo.save(contact);
        
    }

    @Override
    public Contact update(Contact contact) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public List<Contact> getAll() {
       return contactRepo.findAll();
    }

    @Override
    public Contact getById(String id) {
        return contactRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Contact not found with given id "+id));

    }

    @Override
    public void delete(String id) {
       var contact = contactRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Contact not found with given id "+id));
       System.out.println("\n\ncontact"+contact);
       contactRepo.delete(contact);
    }

    @Override
    public List<Contact> search(String name, String email, String phoneNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'search'");
    }

    
    @Override
    public List<Contact> getByUserId(String userId) {
       return contactRepo.findByUserId(userId);
    }

    @Override
    public Page<Contact> getByUser(User user,int page,int size,String sortBy, String direction){
      
        Sort sort = direction.equals("desc")?Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size,sort);
        
        return contactRepo.findByUser(user,pageable);
    }

    @Override
    public Page<Contact> searchByName(String query, int page,int size,String sortBy, String direction) {
        if (size < 1) {
            size = 10; 
        }
       
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;


        Pageable pageable = PageRequest.of(0,size,Sort.by(sortDirection,sortBy));
       
        List<Contact> results =  contactRepo.findByNameContainingIgnoreCase(query);
        System.out.println("\n\nresults"+results);
        System.out.println("\nPage number: " + page);
        Page<Contact> result = contactRepo.findByNameContainingIgnoreCase(query,pageable);
        System.out.println("\n\n\n\n\nresult "+result.getContent());
        return result;
    }

    
}

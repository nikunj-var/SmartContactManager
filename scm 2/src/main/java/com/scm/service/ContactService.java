package com.scm.service;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.scm.entities.Contact;
import com.scm.entities.User;

public interface ContactService {
    Contact save(Contact contact);
    Contact update(Contact contact);
    List<Contact> getAll();
    Contact getById(String id);
    void delete(String id);
    List<Contact> search(String name,String email,String phoneNumber);
    List<Contact> getByUserId(String userId);
    Page<Contact> searchByName(String name,int size,int page,String sortBy, String direction);
    Page<Contact> getByUser(User user,int page,int size,String sortField,String sortDirection);
}

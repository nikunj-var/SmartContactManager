package com.scm.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scm.entities.Contact;
import com.scm.entities.User;

public interface ContactRepo extends JpaRepository<Contact,String> {
    Page<Contact> findByUser(User user,Pageable pageable);

    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    List<Contact> findByUserId(@Param("userId") String userId);

    List<Contact> findByNameContainingIgnoreCase(String name);

    Page<Contact> findByNameContainingIgnoreCase(String name,Pageable pageable);
    // Page<Contact> searchByName(String name,Pageable.unpaged());
}

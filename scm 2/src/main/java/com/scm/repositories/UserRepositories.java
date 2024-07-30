package com.scm.repositories;

import java.util.Optional;

import com.scm.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositories extends JpaRepository<User,String>{

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email,String password);
}
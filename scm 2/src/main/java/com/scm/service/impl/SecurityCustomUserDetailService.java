package com.scm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scm.repositories.UserRepositories;

import java.util.ArrayList;

@Service
public class SecurityCustomUserDetailService implements UserDetailsService{

    @Autowired private UserRepositories userRepositories;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       
        UserDetails user = userRepositories.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found with this email"));
       
    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        new ArrayList<>()
    );
    }
    
}

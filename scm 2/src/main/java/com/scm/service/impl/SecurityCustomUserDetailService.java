package com.scm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scm.repositories.UserRepositories;

@Service
public class SecurityCustomUserDetailService implements UserDetailsService{

    @Autowired private UserRepositories userRepositories;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userRepositories.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found with this email"));
    }
    
}

package com.scm.service.impl;

import java.util.List;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.repositories.UserRepositories;
import com.scm.service.UserService;
import com.scm.helpers.*;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepositories userRepositories;

    @Autowired private PasswordEncoder passwordEncoder;

    private Logger logger =  LoggerFactory.getLogger(getClass());

    @Override
    public User saveUser(User user) {



        // generate userid dynamically
        String userId = UUID.randomUUID().toString();
     
        // encode userid
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

      // user.setRoleList(List.of(AppConstants.ROLE_USER));
      //   logger.info(user.getProvider().toString());
        return userRepositories.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
      return userRepositories.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User user2 = userRepositories.findById(user.getUserId()).orElseThrow(()->new ResourceNotFoundException("user not found"));
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider() );
        user2.setProviderUserId(user.getProviderUserId());

        if(user.getProvider() == null){
          user2.setProvider(Providers.SELF);
        }
        User save = userRepositories.save(user2);

        return Optional.ofNullable(save);
    }

    @Override
    public void deleteUser(String id) {
       User user2 = userRepositories.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
       userRepositories.delete(user2);
    }

    @Override
    public boolean isUserExist(String emailId) {
       User user = userRepositories.findById(emailId).orElse(null);
       return user!=null ? true:false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user = userRepositories.findByEmail(email).orElse(null);
        return user!=null ? true:null;
    }

    @Override
    public List<User> getAllUsers() {
      return null;
    }

    @Override
    public User getUserByEmail(String email) {
      User user = userRepositories.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("user not found"));
      return user;
    }

}
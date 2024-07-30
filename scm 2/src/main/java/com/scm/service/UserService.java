package com.scm.service;

import com.scm.entities.User;
import java.util.*;

public interface UserService {
    User saveUser(User user);
    Optional<User> getUserById(String id);
    Optional<User> updateUser(User user);
    void deleteUser(String id);
    boolean isUserExist(String emailId);
    boolean isUserExistByEmail(String email);
    List<User> getAllUsers();
}

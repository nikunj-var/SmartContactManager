package com.scm.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.scm.entities.Contact;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    private String userId;
    @Column(name="user_name",nullable = false)
    private String name;
    @Column(unique = true,nullable=false)
    private String email;
    private String password;
    private String profilePic;
    private String about;
    private String phoneNumber;
    private String providerUserId;

    private boolean enabled = false;
    private boolean emailVerified = false;
    private boolean phoneVerified = false;
    
    private Providers provider = Providers.SELF;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Contact>contacts = new ArrayList<>();

   
}

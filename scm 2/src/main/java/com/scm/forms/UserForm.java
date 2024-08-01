package com.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {

    @NotBlank(message = "username is required")
    @Size(min=3,message = "minimum 2 characters is required")
    private String name;

    @Email(message = "invalid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6,message = "minimum 6 characters is required")
    private String password;
    
    private String phoneNumber;
    private String about;

    
}

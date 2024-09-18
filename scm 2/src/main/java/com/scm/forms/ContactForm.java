package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import com.scm.validators.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class ContactForm {

    @NotBlank(message = "Name is required")
    private String name;
    @Email(message = "Invalid email Address")
    private String email;
    @NotBlank(message = "PhoneNo. is required")
    private String phoneNo;
    @NotBlank(message = "Address is required")
    private String address;
    private String description;
    private boolean favourite;
    private String linkedIn;
    private String websiteLink;

    // @ValidFile
    // private MultipartFile contactImage;

}

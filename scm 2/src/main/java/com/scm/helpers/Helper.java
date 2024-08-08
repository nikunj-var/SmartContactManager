package com.scm.helpers;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {
    public static String getEmailOfLoggedInUser(org.springframework.security.core.Authentication authentication){
       
        String name="";
        System.out.println("Authentication ="+authentication);
        if(authentication instanceof OAuth2AuthenticationToken){
            var oAuth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;
            System.out.println("oauth2authentication = "+oAuth2AuthenticationToken);
            var clientId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            var oauth2User = (OAuth2User)authentication.getPrincipal();
            System.out.println(oauth2User);
            if(clientId.equalsIgnoreCase("google")){
               name = (String)oauth2User.getAttribute("email").toString();
            }
            else if(clientId.equalsIgnoreCase("github")){
                name = (String)oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString() : oauth2User.getAttribute("login").toString() + "@gmail.com";
            }
        }
        else{
            return authentication.getName();
        }

        
        return name;
    }
}

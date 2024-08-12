package com.scm.config;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.repositories.UserRepositories;
import com.scm.service.impl.TokenService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class); 

    @Autowired private UserRepositories userRepositories;

    @Autowired private TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
    
                var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

                String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

                var oauthUser = (DefaultOAuth2User)authentication.getPrincipal();

               User user1 = new User();
               user1.setUserId(UUID.randomUUID().toString());
               user1.setEmailVerified(true);
               user1.setEnabled(true);
    

                if(authorizedClientRegistrationId.equalsIgnoreCase("google")){
                    user1.setEmail(oauthUser.getAttribute("email").toString());
                    user1.setProfilePic(oauthUser.getAttribute("picture").toString());
                    user1.setName(oauthUser.getAttribute("name").toString());
                    user1.setProviderUserId(oauthUser.getName());
                    user1.setProvider(Providers.GOOGLE);
                    user1.setAbout("This account is created using google");
                }
                else if(authorizedClientRegistrationId.equalsIgnoreCase("github")){
                    String email = oauthUser.getAttribute("email")!=null?oauthUser.getAttribute("email").toString():oauthUser.getAttribute("login").toString()+"@gmail.com";
                    String picture = oauthUser.getAttribute("avatar_url").toString();
                    String name = oauthUser.getAttribute("login").toString();
                    String providerUserId = oauthUser.getName();
                    user1.setEmail(email);
                    user1.setProfilePic(picture);
                    user1.setName(name);
                    user1.setProviderUserId(providerUserId);
                    user1.setProvider(Providers.GITHUB);
                    user1.setAbout("This account is created using github");
                }
              
                
                User userexists = userRepositories.findByEmail(user1.getEmail()).orElse(null);
                if(userexists == null){
                    userRepositories.save(user1);
                    logger.info("user saved ");
                }
                String token = tokenService.generateToken(authentication);
                new DefaultRedirectStrategy().sendRedirect(request, response, "http://localhost:3001/?isauthenticated=true&token="+token);
    }

}

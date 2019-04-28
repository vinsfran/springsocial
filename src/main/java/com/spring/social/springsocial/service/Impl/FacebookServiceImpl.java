package com.spring.social.springsocial.service.Impl;

import com.spring.social.springsocial.service.FacebookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

@Service
public class FacebookServiceImpl implements FacebookService {

    @Value("${spring.social.facebook.app-id}")
    private String facebookId;
    @Value("${spring.social.facebook.app-secret}")
    private String facebookSecret;

    private String uri = "http://localhost:3000/facebook";

    private FacebookConnectionFactory createFacebookConnection() {
        return new FacebookConnectionFactory(facebookId, facebookSecret);
    }

    @Override
    public String facebooklogin() {
        OAuth2Parameters parameters = new OAuth2Parameters();
        parameters.setRedirectUri(uri);
        parameters.setScope("public_profile,email");
        return createFacebookConnection().getOAuthOperations().buildAuthenticateUrl(parameters);
    }

    @Override
    public String getFacebookAccessToken(String code) {
        return createFacebookConnection().getOAuthOperations().exchangeForAccess(code, uri, null).getAccessToken();
    }

    @Override
    public User getFacebookUserProfile(String accessToken) {
        Facebook facebook = new FacebookTemplate(accessToken);
        String[] fields = {"id", "first_name", "last_name", "picture", "email"};

        User user = facebook.fetchObject("me", User.class, fields);

        return user;
    }
}

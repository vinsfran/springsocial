package com.spring.social.springsocial.service.Impl;

import com.spring.social.springsocial.service.GoogleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

@Service
public class GoogleServiceImpl implements GoogleService {

    @Value("${spring.social.google.app-id}")
    private String googleId;
    @Value("${spring.social.google.app-secret}")
    private String googleSecret;

    private String uri = "http://localhost:3000/google";

    private GoogleConnectionFactory createGoogleConnection() {
        return new GoogleConnectionFactory(googleId, googleSecret);
    }

    @Override
    public String googleLogin() {
        OAuth2Parameters parameters = new OAuth2Parameters();
        parameters.setRedirectUri(uri);
        parameters.setScope("profile");
        return createGoogleConnection().getOAuthOperations().buildAuthenticateUrl(parameters);
    }

    @Override
    public String getGoogleAccessToken(String code) {
        return createGoogleConnection().getOAuthOperations().exchangeForAccess(code, uri, null).getAccessToken();
    }

    @Override
    public Person getGoogleUserProfile(String accessToken) {
        Google google = new GoogleTemplate(accessToken);
        Person person = google.plusOperations().getGoogleProfile();
        return person;
    }
}

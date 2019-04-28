package com.spring.social.springsocial.service.Impl;

import com.spring.social.springsocial.service.LinkedinService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.User;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.LinkedInProfileFull;
import org.springframework.social.linkedin.api.impl.LinkedInTemplate;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

@Service
public class LinkedinServiceImpl implements LinkedinService {

    @Value("${spring.social.linkedin.app-id}")
    private String linkedinId;
    @Value("${spring.social.linkedin.app-secret}")
    private String linkedinSecret;

    private String uri = "http://localhost:3000/linkedin";

    private LinkedInConnectionFactory createLinkedInConnection() {
        return new LinkedInConnectionFactory(linkedinId, linkedinSecret);
    }

    @Override
    public String linkedinLogin() {
        OAuth2Parameters parameters = new OAuth2Parameters();
        parameters.setRedirectUri(uri);
        parameters.setScope("r_liteprofile,r_emailaddress");
        return createLinkedInConnection().getOAuthOperations().buildAuthenticateUrl(parameters);
    }

    @Override
    public String getLinkedinAccessToken(String code) {
        return createLinkedInConnection().getOAuthOperations().exchangeForAccess(code, uri, null).getAccessToken();
    }

    @Override
    public LinkedInProfileFull getLinkedinUserProfile(String accessToken) {
        LinkedIn linkedIn = new LinkedInTemplate(accessToken);
        LinkedInProfileFull linkedInProfileFull = linkedIn.profileOperations().getUserProfileFull();
        return linkedInProfileFull;
    }
}

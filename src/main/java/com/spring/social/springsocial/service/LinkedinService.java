package com.spring.social.springsocial.service;

import org.springframework.social.linkedin.api.LinkedInProfileFull;

public interface LinkedinService {

    String linkedinLogin();

    String getLinkedinAccessToken(String code);

    LinkedInProfileFull getLinkedinUserProfile(String accessToken);
}

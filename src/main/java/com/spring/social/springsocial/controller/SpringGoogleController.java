package com.spring.social.springsocial.controller;

import com.spring.social.springsocial.model.UserInfo;
import com.spring.social.springsocial.service.GoogleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.google.api.plus.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class SpringGoogleController {

    @Autowired
    private GoogleService googleService;

    @GetMapping(value = "/googlelogin")
    public RedirectView googlelogin() {
        RedirectView redirectView = new RedirectView();
        String url = googleService.googleLogin();
        System.out.println(url);
        redirectView.setUrl(url);
        return redirectView;
    }

    @GetMapping(value = "/google")
    public String google(@RequestParam("code") String code) {
        String accessToken = googleService.getGoogleAccessToken(code);
        return "redirect:/googleprofiledata/" + accessToken;
    }

    @GetMapping(value = "/googleprofiledata/{accessToken:.+}")
    public String googleprofiledata(@PathVariable String accessToken, Model model) {
        Person user = googleService.getGoogleUserProfile(accessToken);
        UserInfo userInfo = new UserInfo(user.getGivenName(), user.getFamilyName(), user.getImageUrl());
        model.addAttribute("user", userInfo);
        return "view/userprofile";
    }
}

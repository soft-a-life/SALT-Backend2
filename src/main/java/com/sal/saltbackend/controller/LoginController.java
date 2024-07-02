package com.sal.saltbackend.controller;

import com.sal.saltbackend.dto.user_dto;
import com.sal.saltbackend.entity.user;
import com.sal.saltbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/kakao/signin")
    public String kakaologin() {
        System.out.println("here");
        return "login";
    }

//    @GetMapping("/oauth2/code/kakao")
//    public String hi(){
//        System.out.println("here2");
//        return "loginsuccess";
//    }

//    @GetMapping("/success")
//    public String success() {
//        // 로그인 성공 후 loginsuccess.html로 리다이렉트
//        return "loginsuccess";
//    }

    @GetMapping("/success")
    public user_dto success() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        Optional<user> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            user user = userOptional.get();
            String userUuid = user.getUserUuid() != null ? user.getUserUuid().toString() : null;
            return new user_dto(user.getNickname(), user.getEmail(), userUuid);
        } else {
            // 예외 처리
            throw new RuntimeException("User not found");
        }
    }
}


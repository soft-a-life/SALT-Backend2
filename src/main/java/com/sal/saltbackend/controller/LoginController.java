package com.sal.saltbackend.controller;

import com.sal.saltbackend.dto.user_dto;
import com.sal.saltbackend.entity.user;
import com.sal.saltbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
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

//    @GetMapping("/success")
//    public user_dto success() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        // 인증된 사용자 정보를 가져옵니다.
//        DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
//        // 사용자 정보를 추출합니다.
//        String id = defaultOAuth2User.getAttribute("id").toString();
//        Map<String, Object> profileInfo = defaultOAuth2User.getAttribute("kakao_account");
//        String nickname = (String) ((Map<String, Object>) profileInfo.get("profile")).get("nickname");
//        String email = (String) profileInfo.get("email");
//
//        System.out.println("id : " + id);
//        System.out.println("nickname : " +  nickname);
//        System.out.println("emai :" + email);
//
//        Optional<user> userOptional = userRepository.findByEmail(email);
//
//        if (userOptional.isPresent()) {
//            user user = userOptional.get();
//            String userUuid = user.getUserUuid() != null ? user.getUserUuid().toString() : null;
//            System.out.println("here");
//            return new user_dto(user.getNickname(), user.getEmail(), userUuid);
//        } else {
//            // 예외 처리
//            throw new RuntimeException("User not found");
//        }
//    }
}


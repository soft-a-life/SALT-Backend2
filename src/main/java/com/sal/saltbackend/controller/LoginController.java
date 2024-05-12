package com.sal.saltbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/kakao/signin")
public class LoginController {

    @GetMapping
    public  String login(){

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        // 사용자 정보가 OAuth2User 타입인 경우에만 처리
//        if (authentication.getPrincipal() instanceof OAuth2User) {
//            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
//
//            // 사용자의 id, email, nickname을 가져옵니다.
//            String id = oauth2User.getAttribute("id").toString();
//            String email = (String) oauth2User.getAttribute("email");
//            String nickname = (String) ((Map<String, Object>) oauth2User.getAttribute("kakao_account").get("profile")).get("nickname");
//
//            // 이후 원하는 동작 수행
//            // 예를 들어, 사용자 정보를 데이터베이스에 저장하거나, 이미 저장된 정보와 비교하여 권한 부여 등의 동작 수행
//            // ...
//
//            // 로그인 성공 후, 이동할 페이지를 리턴합니다.
//            return "redirect:/success"; // 성공 페이지 URL로 변경하세요
//        }

        return "login";
    }
//    @GetMapping("/")
//    @ResponseBody
//    public String main()
//    {
//        return "Hello world";
//    }
//
//    @RequestMapping("/login")
//    @ResponseBody
//    public String login()
//    {
//        return "index";
//    }




}

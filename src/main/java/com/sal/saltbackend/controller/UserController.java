package com.sal.saltbackend.controller;


import com.sal.saltbackend.dto.email_dto;
import com.sal.saltbackend.entity.user;
import com.sal.saltbackend.serivce.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private Userservice userservice;

    @PostMapping("/profile")
    @ResponseBody
    public ResponseEntity<?> userProfile(@RequestBody email_dto request){
        Optional<user> userOptional = userservice.userProfile(request.getEmail());
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
//            return ResponseEntity.notFound().build();
            return ResponseEntity.status(404).body("User not found");
        }
    }

    @GetMapping("/useruuid")
    public ResponseEntity<String> userChangeUuid(
            @RequestParam(required = true) String email,
            @RequestParam(required = true) String useruuid
            ){

        Optional<user> userOptional = userservice.userProfile(email);
        if (userOptional.isPresent())
        {
            user user_one = userOptional.get();
            user_one.setUserUuid(useruuid);
            userservice.saveUser(user_one);
            return ResponseEntity.ok(useruuid);
        }
        else
        {
            return ResponseEntity.status(404).body("User not found");
        }
    }




}

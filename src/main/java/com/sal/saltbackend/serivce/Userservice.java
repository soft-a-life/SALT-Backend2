package com.sal.saltbackend.serivce;

import com.sal.saltbackend.entity.user;
import com.sal.saltbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Userservice{

    @Autowired
    private UserRepository userRepository;

    public Optional<user> userProfile(String email){
        return userRepository.findByEmail(email);
    }

    public user saveUser(user user_one){
        return userRepository.save(user_one);
    }

    public Optional<user> findById(Long id) {
        return Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

}

package com.sal.saltbackend.repository;

import com.sal.saltbackend.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<user, Long> {
    // 추가적인 쿼리 메소드가 필요하면 여기에 작성
    Optional<user> findByEmail(String email);



}

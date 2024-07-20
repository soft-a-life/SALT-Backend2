package com.sal.saltbackend.repository;

import com.sal.saltbackend.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

}

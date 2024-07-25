package com.sal.saltbackend.repository;

import com.sal.saltbackend.entity.board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<board, Long> {

}

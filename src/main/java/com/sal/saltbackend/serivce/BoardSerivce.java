package com.sal.saltbackend.serivce;

import com.sal.saltbackend.dto.board_dto;
import com.sal.saltbackend.entity.Board;
import com.sal.saltbackend.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoardSerivce {
    @Autowired
    private BoardRepository boardRepository;

    public void createBoard(Board board ){
        boardRepository.save(board);
    }
}

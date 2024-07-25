package com.sal.saltbackend.serivce;

import com.sal.saltbackend.entity.board;
import com.sal.saltbackend.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardSerivce {
    @Autowired
    private BoardRepository boardRepository;

    public void createBoard(board board ){
        boardRepository.save(board);
    }

    public List<board> getAllBoards() {
        return boardRepository.findAll();
    }
}

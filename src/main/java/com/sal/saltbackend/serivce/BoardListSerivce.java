package com.sal.saltbackend.serivce;


import com.sal.saltbackend.entity.BoardList;
import com.sal.saltbackend.repository.BoardListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardListSerivce {

    @Autowired
    private BoardListRepository boardListRepository;

    public void createBoardList(BoardList boardList) {
        boardListRepository.save(boardList);
    }
}

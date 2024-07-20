package com.sal.saltbackend.controller;

import com.sal.saltbackend.dto.board_dto;
import com.sal.saltbackend.entity.Board;
import com.sal.saltbackend.entity.BoardList;
import com.sal.saltbackend.entity.user;
import com.sal.saltbackend.serivce.BoardListSerivce;
import com.sal.saltbackend.serivce.BoardSerivce;
import com.sal.saltbackend.serivce.Userservice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
public class BoardController {
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    @Autowired
    private BoardSerivce boardSerivce;

    @Autowired
    private Userservice userservice;

    @Autowired
    private BoardListSerivce boardListSerivce;

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<String> createBoard(@RequestBody board_dto boardDto) {
        // boardDto 객체 로깅
//        logger.info("Received boardDto: {}", boardDto);
        logger.info("Received boardDto: {}", boardDto.toString());
        //게시글을 저장하는 DB
        Board board = new Board();
        board.setBoard_content(boardDto.getBoard_content());
        board.setBoard_title(boardDto.getBoard_title());
        board.setCategoru(boardDto.getCategoru());
        boardSerivce.createBoard(board);


        //사용자 검사 진행해야됨
        user user_one = userservice.findById(boardDto.getId());
        BoardList boardList = new BoardList();
        boardList.setUser(user_one);
        boardList.setBoard(board);
        boardListSerivce.createBoardList(boardList);


        return ResponseEntity.ok("create Board");
    }


}

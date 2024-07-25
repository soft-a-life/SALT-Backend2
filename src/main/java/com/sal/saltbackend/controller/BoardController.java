package com.sal.saltbackend.controller;

import com.sal.saltbackend.dto.board_dto;
import com.sal.saltbackend.entity.board;
import com.sal.saltbackend.entity.user;
import com.sal.saltbackend.serivce.BoardSerivce;
import com.sal.saltbackend.serivce.Userservice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/board")
public class BoardController {
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    @Autowired
    private BoardSerivce boardSerivce;

    @Autowired
    private Userservice userservice;

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<String> createBoard(@RequestBody board_dto boardDto) {
        // boardDto 객체 로깅
//        logger.info("Received boardDto: {}", boardDto);
        logger.info("Received boardDto: {}", boardDto.toString());

        //사용자 검사 진행해야됨
        Optional<user> user_one = userservice.findById(boardDto.getId());

        if (user_one.isPresent())
        {
            user user = user_one.get();
            //게시글을 저장하는 DB
            board board = new board();

            board.setBoard_content(boardDto.getBoard_content());
            board.setBoard_title(boardDto.getBoard_title());
            board.setCategoru(boardDto.getCategoru());

            // Set the user in the Board entity
            board.setUser(user);
            boardSerivce.createBoard(board);
            return ResponseEntity.ok("create Board Success");
        }
        else
        {
            return ResponseEntity.status(400).body("User not found");
        }
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<board>> getAllBoards(){
        List<board> boards = boardSerivce.getAllBoards();
        return ResponseEntity.ok(boards);
    }



}

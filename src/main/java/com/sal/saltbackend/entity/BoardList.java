package com.sal.saltbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "board_list")
public class BoardList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private user user;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    public void setUser(com.sal.saltbackend.entity.user user) {
        this.user = user;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}

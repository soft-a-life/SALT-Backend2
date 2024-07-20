package com.sal.saltbackend.dto;

import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class board_dto {
    private long id;

    private String board_title;

    private String board_content;

    private String category;

    public String getBoard_content() {
        return board_content;
    }

    public String getBoard_title() {
        return board_title;
    }

    public long getId() {
        return id;
    }

    public String getCategoru() {
        return category;
    }
    @Override
    public String toString() {
        return "board_dto{" +
                "board_content='" + board_content + '\'' +
                ", board_title='" + board_title + '\'' +
                ", categoru='" + category + '\'' +
                '}';
    }
}

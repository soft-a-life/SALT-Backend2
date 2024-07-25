package com.sal.saltbackend.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
                ", category='" + category + '\'' +
                '}';
    }

    public void setBoardContent(String board_content) {
        this.board_content = board_content;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBoardTitle(String board_title) {
        this.board_title = board_title;
    }

    public void setId(long id) {
        this.id = id;
    }
}

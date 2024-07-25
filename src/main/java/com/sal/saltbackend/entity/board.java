package com.sal.saltbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Setter
@Table(name = "board")
public class board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private user user;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime create_date;

    @Column(nullable = false)
    private String board_title;

    @Column(nullable = false)
    private String board_content;

    @Column(nullable = false)
    private String category;

    public void setBoard_content(String board_content) {
        this.board_content = board_content;
    }

    public void setBoard_title(String board_title) {
        this.board_title = board_title;
    }

    public void setCategoru(String category) {
        this.category = category;
    }

    public void setUser(com.sal.saltbackend.entity.user user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getBoard_title() {
        return board_title;
    }

    public String getBoard_content() {
        return board_content;
    }

    public String getCategory() {
        return category;
    }
}

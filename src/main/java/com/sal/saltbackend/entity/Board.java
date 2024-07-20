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
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime create_date;

    @Column(nullable = false)
    private String board_title;

    @Column(nullable = false)
    private String board_content;

    @Column(nullable = false)
    private String categoru;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<BoardList> boardLists;

    public void setBoard_content(String board_content) {
        this.board_content = board_content;
    }

    public void setBoard_title(String board_title) {
        this.board_title = board_title;
    }

    public void setCategoru(String categoru) {
        this.categoru = categoru;
    }
}

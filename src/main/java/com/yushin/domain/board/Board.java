package com.yushin.domain.board;

import com.yushin.web.dto.board.BoardRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    public Board(String title, String content) {
        this.title = title;
        this.content = content;
    }


    public static Board from (BoardRequestDto boardRequestDto){
        return new Board(boardRequestDto.getTitle(), boardRequestDto.getContent());
    }
}



package com.main.board.mainBoard.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailFromDB {
    private Long postId;
    private String email; //작성자 명
}

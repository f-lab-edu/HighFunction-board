package com.main.board.mainBoard.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DB_CommentCount {
    private Long postId;
    private Long commentCount;

}

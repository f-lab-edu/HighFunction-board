package com.main.board.mainBoard.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCountFromDB {
    private Long postId;
    private Long commentCount;

}

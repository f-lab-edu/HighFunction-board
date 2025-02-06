package com.main.board.mainBoard.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCountResponse {
    private Long commentCount;

    public CommentCountResponse(Long count) {
        this.commentCount = count;
    }
}

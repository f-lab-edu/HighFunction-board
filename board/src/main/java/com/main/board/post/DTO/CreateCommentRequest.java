package com.main.board.post.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateCommentRequest {

    private long memberId;
    private Long parentId;
    private String commentContent;
}

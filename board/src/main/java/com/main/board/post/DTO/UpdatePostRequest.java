package com.main.board.post.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePostRequest {

    private Long postId;
    private Long memberId;
    private String postTitle;
    private String postContent;
}

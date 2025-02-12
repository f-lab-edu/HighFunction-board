package com.main.board.mainBoard.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {
    private Long postId;
    private String postTitle;
    private Long viewCount;
    private Long likeCount;
    private Long badCount;
    private LocalDateTime createdAt;
}

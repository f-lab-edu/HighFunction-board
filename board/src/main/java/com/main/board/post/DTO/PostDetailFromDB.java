package com.main.board.post.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostDetailFromDB {
    private Long postId;
    private String postTitle;
    private String postContent;
    private Long viewCount;
    private Long likeCount;
    private Long badCount;
    private LocalDateTime createdAt;
    private String email;
    private Long commentCount;
}

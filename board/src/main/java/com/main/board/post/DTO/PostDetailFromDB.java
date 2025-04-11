package com.main.board.post.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostDetailFromDB {
    private long postId;
    private String postTitle;
    private String postContent;
    private long viewCount;
    private long likeCount;
    private long badCount;
    private LocalDateTime createdAt;
    private String authorEmail;
    private long commentCount;
    private long hasImage;
}

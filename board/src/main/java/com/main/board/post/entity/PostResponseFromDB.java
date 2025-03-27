package com.main.board.post.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseFromDB {

    private long postId;
    private String postTitle;
    private String postContent;
    private long viewCount;
    private long likeCount;
    private long badCount;
    private String authorEmail;
    private Long commentCount;
    private LocalDateTime createdAt;
}

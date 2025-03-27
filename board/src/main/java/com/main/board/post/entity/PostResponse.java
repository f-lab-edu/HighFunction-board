package com.main.board.post.entity;

import com.main.board.post.DTO.CommentDetailFromDB;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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
    private String authorEmail;
    private Long commentCount;
    private boolean hasChild;

    @Builder
    public PostResponse(Long postId, String postTitle, Long viewCount, Long likeCount, Long badCount, LocalDateTime createdAt, String authorEmail, Long commentCount, boolean hasChild) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.badCount = badCount;
        this.createdAt = createdAt;
        this.authorEmail = authorEmail;
        this.commentCount = commentCount;
        this.hasChild = hasChild;
    }
}

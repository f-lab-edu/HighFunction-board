package com.main.board.mainBoard.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MainBoardPostResponse {
    private Long postId;
    private String postTitle;
    private Long viewCount;
    private Long likeCount;
    private Long badCount;
    private LocalDateTime createdAt;
    private String email; //작성자 명
    private Long commentCount;

    public MainBoardPostResponse(DB_MainBoardData data) {
        this.postId = data.getPostId();
        this.postTitle = data.getPostTitle();
        this.viewCount = data.getViewCount();
        this.likeCount = data.getLikeCount();
        this.badCount = data.getBadCount();
        this.createdAt = data.getCreatedAt();
        this.email = data.getEmail();
        this.commentCount = data.getCommentCount();
    }
}

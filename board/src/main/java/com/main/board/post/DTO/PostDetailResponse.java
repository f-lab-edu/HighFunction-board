package com.main.board.post.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class PostDetailResponse {

    private Long postId;
    private String postTitle;
    private Long viewCount;
    private Long likeCount;
    private Long badCount;
    private LocalDateTime createdAt;
    private String authorEmail;
    private List<CommentDetailFromDB> commentList;

    public PostDetailResponse(PostDetailFromDB postDetailList, List<CommentDetailFromDB> commentList) {
        this.setPostId(postDetailList.getPostId());
        this.setPostTitle(postDetailList.getPostTitle());
        this.setViewCount(postDetailList.getViewCount());
        this.setLikeCount(postDetailList.getLikeCount());
        this.setBadCount(postDetailList.getBadCount());
        this.setCreatedAt(postDetailList.getCreatedAt());
        this.setAuthorEmail(postDetailList.getAuthorEmail());
        this.setCommentList(commentList);
    }

}

package com.main.board.post.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MoreCommentResponse {
    private long commentId;
    private String commentContent;
    private long likeCount;
    private LocalDateTime createdAt;
    private String authorEmail;
    private Long parentId;
    private boolean hasChild;

    public MoreCommentResponse(CommentDetailFromDB commentList) {
        this.setCommentId(commentList.getCommentId());
        this.setCommentContent(commentList.getCommentContent());
        this.setLikeCount(commentList.getLikeCount());
        this.setCreatedAt(commentList.getCreatedAt());
        this.setAuthorEmail(commentList.getEmail());
        this.setParentId(commentList.getParentId());
        this.setHasChild(commentList.isHasChild());
    }
}

package com.main.board.post.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class CommentDetailFromDB {
    private Long commentId;
    private String commentContent;
    private Long likeCount;
    private LocalDateTime createdAt;
    private String email;
    private List<ChildCommentFromDB> childCommentList;
}

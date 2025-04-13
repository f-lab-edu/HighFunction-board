package com.main.board.post.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class CommentDetailFromDB {
    private long commentId;
    private String commentContent;
    private long likeCount;
    private LocalDateTime createdAt;
    private String email;
    private Long parentId;
    private boolean hasChild;
    private List<CommentDetailFromDB> childCommentList  = new ArrayList<>();;
}

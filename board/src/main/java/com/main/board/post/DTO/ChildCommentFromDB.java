package com.main.board.post.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class ChildCommentFromDB {
    private Long childCommentId;
    private String childCommentContent;
    private LocalDateTime createdAt;
    private Long likeCount;
    private String email;

}

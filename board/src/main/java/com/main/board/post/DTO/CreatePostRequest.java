package com.main.board.post.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class CreatePostRequest {
    private long memberId;
    @NotNull(message = "제목을 입력해주세요")
    private String postTitle;
    @NotNull(message = "내용을 입력해주세요")
    private String postContent;

    private CreatePostRequest(long memberId, String postTitle, String postContent) {
        this.memberId = memberId;
        this.postTitle = postTitle;
        this.postContent = postContent;
    }
}

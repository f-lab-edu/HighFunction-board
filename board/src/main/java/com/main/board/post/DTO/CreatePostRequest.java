package com.main.board.post.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class CreatePostRequest {
    private Long memberId;
    @NotBlank(message = "제목을 입력해주세요")
    @NotNull(message = "제목을 입력해주세요")
    private String postTitle;
    @NotBlank(message = "내용을 입력해주세요")
    @NotNull(message = "내용을 입력해주세요")
    private String postContent;
}

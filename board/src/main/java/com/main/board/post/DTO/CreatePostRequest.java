package com.main.board.post.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class CreatePostRequest {
    private Long memberId;
    @NotBlank(message = "제목을 입력해주세요")
    private String postTitle;
    @NotBlank(message = "내용을 입력해주세요")
    private String postContent;

    private List<MultipartFile> postImageList;

}

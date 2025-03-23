package com.main.board.post.controller;

import com.main.board.post.DTO.PostDetailResponse;
import com.main.board.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;


    @GetMapping("/{postId}")
    public PostDetailResponse getPostDetail(@PathVariable Long postId) {
        PostDetailResponse postDetailResponseList = postService.getPostDetail(postId);
        return postDetailResponseList;
    }
}

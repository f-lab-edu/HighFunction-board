package com.main.board.post.controller;

import com.main.board.post.DTO.MoreCommentResponse;
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


    /*
    서브쿼리방식
    */
    @GetMapping("/{postId}")
    public PostDetailResponse getPostDetail(@PathVariable long postId,
                                            @RequestParam(required = false, defaultValue = "0") int page) {
        long offset = page != 0 ? (page - 1) * 10 : 0;
        return postService.getPostDetail(postId, offset);
    }

    //대댓글/대대댓글 더보기 기능
    @GetMapping({"/comment/{commentId}", "/comment/{commentId}/more"})
    public List<MoreCommentResponse> getMoreComment(@PathVariable long commentId,
                                                    @RequestParam(required = false, defaultValue = "0") int page) {
        long offset = page != 0 ? (page - 1) * 10 : 0;
        return postService.getMoreComment(commentId, offset);
    }

    //서브쿼리방식 끝

    /*
     join 방식
     Group by와 DISTICT를 차이 모름
    */
    @GetMapping("join/{postId}")
    public PostDetailResponse getJoinPostDetail(@PathVariable long postId,
                                            @RequestParam(required = false, defaultValue = "0") int page) {
        long offset = page != 0 ? (page - 1) * 10 : 0;
        return postService.getJoinPostDetail(postId, offset);
    }

    @GetMapping({"join/comment/{commentId}", "join/comment/{commentId}/more"})
    public List<MoreCommentResponse> getJoinMoreComment(@PathVariable long commentId,
                                                    @RequestParam(required = false, defaultValue = "0") int page) {
        long offset = page != 0 ? (page - 1) * 10 : 0;
        return postService.getJoinMoreComment(commentId, offset);
    }
    //  join 방식 끝

    //recursive 방식
    @GetMapping("recursive/{postId}")
    public PostDetailResponse getRecursivePostDetail(@PathVariable long postId,
                                                @RequestParam(required = false, defaultValue = "0") int page) {
        long offset = page != 0 ? (page - 1) * 10 : 0;
        return postService.getRecursivePostDetail(postId, offset);
    }

    @GetMapping({"recursive/comment/{commentId}", "recursive/comment/{commentId}/more"})
    public List<MoreCommentResponse> getRecursiveComment(@PathVariable long commentId,
                                                        @RequestParam(required = false, defaultValue = "0") int page) {
        long offset = page != 0 ? (page - 1) * 10 : 0;
        return postService.getRecursiveMoreComment(commentId, offset);
    }
    //recursive 방식 끝


}

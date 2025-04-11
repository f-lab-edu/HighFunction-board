package com.main.board.post.controller;

import com.main.board.post.DTO.*;
import com.main.board.post.service.PostService;
import com.main.board.post.util.PostUtilMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
                                            @RequestParam(required = false, defaultValue = "1") @Min(1)int page) {
        /*
         @Min 사용시에 음수일경우 MethodArgumentNotValidException이 아니라
👉       ConstraintViolationException 예외
         */

        /*
        방식1 메소드2개분리 (가독성을 최우선)
        PostUtilMethod.checkPage(page);
        long offset = PostUtilMethod.calculateOffset(page);
        */

        //방식2 메소드 1개로 합치기
        long offset = PostUtilMethod.calculateOffsetAndCheckPage(page);
        return postService.getPostDetail(postId, offset);
    }

    //대댓글/대대댓글 더보기 기능
    @GetMapping({"/comment/{commentId}"})
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

    @GetMapping({"join/comment/{commentId}"})
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

    @GetMapping({"recursive/comment/{commentId}"})
    public List<MoreCommentResponse> getRecursiveComment(@PathVariable long commentId,
                                                        @RequestParam(required = false, defaultValue = "0") int page) {
        long offset = page != 0 ? (page - 1) * 10 : 0;
        return postService.getRecursiveMoreComment(commentId, offset);
    }
    //recursive 방식 끝


    //게시물 생성
    @PostMapping("createpost")
    public ResponseEntity<String> createPost(@RequestBody @Valid CreatePostRequest createPostRequest) {
                postService.createPost(createPostRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("게시물이 성공적으로 생성되었습니다!");
    }

    @PostMapping("updatepost")
    public ResponseEntity<String> updatePost(@RequestBody @Valid UpdatePostRequest updatePostRequest) {
        postService.updatePost(updatePostRequest);
        return ResponseEntity.status(HttpStatus.OK).body("게시물이 성공적으로 수정되었습니다!");
    }

    @PostMapping("deletepost")
    public ResponseEntity<String> deletePost(@RequestBody @Valid DeletePostRequest deletePostRequest) {
        postService.deletePost(deletePostRequest);
        return ResponseEntity.status(HttpStatus.OK).body("게시물이 성공적으로 삭제되었습니다!");
    }

}

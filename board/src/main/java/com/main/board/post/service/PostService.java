package com.main.board.post.service;

import com.main.board.post.DTO.*;
import com.main.board.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    //Mybatis레포지토리
    private final PostRepository postRepository;



    //1. 서브쿼리방식
    @Transactional(readOnly = true)
    public PostDetailResponse getPostDetail(long postId, long offset) {

        //1. 게시글 정보 가져오기
        PostDetailFromDB postDetailList = postRepository.getPostDetail(postId);
        //2. 댓글 정보 가져오기
        List<CommentDetailFromDB> commentList = postRepository.getCommentList(postId, offset);

        return new PostDetailResponse(postDetailList, commentList);
    }

    //대댓글 더보기 기능
    public List<MoreCommentResponse> getMoreComment(long commentId, long offset) {

        //1. 반환 객체 생성
        List<MoreCommentResponse> moreCommentResponseList = new ArrayList<>();
        //2. 대댓글 정보 가져오기
        List<CommentDetailFromDB> commentList = postRepository.getMoreCommentList(commentId, offset);
        //3. 반환객체에 매핑
        // 3. 대댓글 데이터를 MoreCommentResponse로 변환하여 리스트에 추가
        for (CommentDetailFromDB comment : commentList) {
            // CommentDetailFromDB를 MoreCommentResponse에 매핑
            MoreCommentResponse moreCommentResponse = new MoreCommentResponse(comment);
            moreCommentResponseList.add(moreCommentResponse);  // 리스트에 추가
        }
        return moreCommentResponseList;
    }
    //서브쿼리방식 끝


    //2. join 방식
    @Transactional(readOnly = true)
    public PostDetailResponse getJoinPostDetail(long postId, long offset) {

        //1. 게시글 정보 가져오기
        PostDetailFromDB postDetailList = postRepository.getPostDetail(postId);
        //2. 댓글 정보 가져오기
        List<CommentDetailFromDB> commentList = postRepository.getJoinCommentList(postId, offset);

        return new PostDetailResponse(postDetailList, commentList);
    }

    public List<MoreCommentResponse> getJoinMoreComment(long commentId, long offset) {
        //1. 반환 객체 생성
        List<MoreCommentResponse> moreCommentResponseList = new ArrayList<>();
        //2. 대댓글 정보 가져오기
        List<CommentDetailFromDB> commentList = postRepository.getJoinMoreCommentList(commentId, offset);
        //3. 반환객체에 매핑
        // 3. 대댓글 데이터를 MoreCommentResponse로 변환하여 리스트에 추가
        for (CommentDetailFromDB comment : commentList) {
            // CommentDetailFromDB를 MoreCommentResponse에 매핑
            MoreCommentResponse moreCommentResponse = new MoreCommentResponse(comment);
            moreCommentResponseList.add(moreCommentResponse);  // 리스트에 추가
        }
        return moreCommentResponseList;
    }
    //join 방식 끝

    //3. recursive 방식
    @Transactional(readOnly = true)
    public PostDetailResponse getRecursivePostDetail(long postId, long offset) {

        //1. 게시글 정보 가져오기
        PostDetailFromDB postDetailList = postRepository.getPostDetail(postId);
        //2. 댓글 정보 가져오기
        List<CommentDetailFromDB> commentList = postRepository.getRecursiveCommentList(postId, offset);

        return new PostDetailResponse(postDetailList, commentList);
    }

    public List<MoreCommentResponse> getRecursiveMoreComment(long commentId, long offset) {
        //1. 반환 객체 생성
        List<MoreCommentResponse> moreCommentResponseList = new ArrayList<>();
        //2. 대댓글 정보 가져오기
        List<CommentDetailFromDB> commentList = postRepository.getRecursiveMoreCommentList(commentId, offset);
        //3. 반환객체에 매핑
        // 3. 대댓글 데이터를 MoreCommentResponse로 변환하여 리스트에 추가
        for (CommentDetailFromDB comment : commentList) {
            // CommentDetailFromDB를 MoreCommentResponse에 매핑
            MoreCommentResponse moreCommentResponse = new MoreCommentResponse(comment);
            moreCommentResponseList.add(moreCommentResponse);  // 리스트에 추가
        }
        return moreCommentResponseList;
    }
    //recursive 방식 끝

    public void createPost(CreatePostRequest createPostRequest) {
        postRepository.createPost(createPostRequest);
    }

    public void updatePost(UpdatePostRequest updatePostRequest) {
        boolean checkOwner = postRepository.selectPostForMember(updatePostRequest.getPostId(), updatePostRequest.getMemberId());
        if(!checkOwner) {
            throw new IllegalArgumentException("게시물 작성자가 아닙니다.");
        }
        postRepository.updatePost(updatePostRequest);
    }

    public void deletePost(DeletePostRequest deletePostRequest) {
        boolean checkOwner = postRepository.selectPostForMember(deletePostRequest.getPostId(), deletePostRequest.getMemberId());
        if(!checkOwner) {
            throw new IllegalArgumentException("게시물 작성자가 아닙니다.");
        }
        postRepository.deletePost(deletePostRequest);
    }
}

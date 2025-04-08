package com.main.board.post.service;

import com.main.board.post.DTO.CommentDetailFromDB;
import com.main.board.post.DTO.MoreCommentResponse;
import com.main.board.post.DTO.PostDetailFromDB;
import com.main.board.post.DTO.PostDetailResponse;
import com.main.board.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    //Mybatis레포지토리
    private final PostRepository postRepository;



    //1. 서브쿼리방식
    public PostDetailResponse getPostDetail(long postId, long offset) {

        //1. 반환 객체 생성
        PostDetailResponse postDetailResponseList = new PostDetailResponse();
        //2. 게시글 정보 가져오기
        PostDetailFromDB postDetailList = postRepository.getPostDetail(postId);
        //3. 댓글 정보 가져오기
        List<CommentDetailFromDB> commentList = postRepository.getCommentList(postId, offset);
        //4. 반환객체에 매핑
        postDetailResponseList.settingResponse(postDetailList, commentList);

        return postDetailResponseList;
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
            MoreCommentResponse moreCommentResponse = new MoreCommentResponse();
            moreCommentResponse.settingResponse(comment);  // CommentDetailFromDB를 MoreCommentResponse에 매핑
            moreCommentResponseList.add(moreCommentResponse);  // 리스트에 추가
        }
        return moreCommentResponseList;
    }
    //서브쿼리방식 끝


    //2. join 방식
    public PostDetailResponse getJoinPostDetail(long postId, long offset) {
        //1. 반환 객체 생성
        PostDetailResponse postDetailResponseList = new PostDetailResponse();
        //2. 게시글 정보 가져오기
        PostDetailFromDB postDetailList = postRepository.getPostDetail(postId);
        //3. 댓글 정보 가져오기
        List<CommentDetailFromDB> commentList = postRepository.getJoinCommentList(postId, offset);
        //4. 반환객체에 매핑
        postDetailResponseList.settingResponse(postDetailList, commentList);

        return postDetailResponseList;
    }

    public List<MoreCommentResponse> getJoinMoreComment(long commentId, long offset) {
        //1. 반환 객체 생성
        List<MoreCommentResponse> moreCommentResponseList = new ArrayList<>();
        //2. 대댓글 정보 가져오기
        List<CommentDetailFromDB> commentList = postRepository.getJoinMoreCommentList(commentId, offset);
        //3. 반환객체에 매핑
        // 3. 대댓글 데이터를 MoreCommentResponse로 변환하여 리스트에 추가
        for (CommentDetailFromDB comment : commentList) {
            MoreCommentResponse moreCommentResponse = new MoreCommentResponse();
            moreCommentResponse.settingResponse(comment);  // CommentDetailFromDB를 MoreCommentResponse에 매핑
            moreCommentResponseList.add(moreCommentResponse);  // 리스트에 추가
        }
        return moreCommentResponseList;
    }
    //join 방식 끝

    //3. recursive 방식
    public PostDetailResponse getRecursivePostDetail(long postId, long offset) {
        //1. 반환 객체 생성
        PostDetailResponse postDetailResponseList = new PostDetailResponse();
        //2. 게시글 정보 가져오기
        PostDetailFromDB postDetailList = postRepository.getPostDetail(postId);
        //3. 댓글 정보 가져오기
        List<CommentDetailFromDB> commentList = postRepository.getRecursiveCommentList(postId, offset);
        //4. 반환객체에 매핑
        postDetailResponseList.settingResponse(postDetailList, commentList);

        return postDetailResponseList;
    }

    public List<MoreCommentResponse> getRecursiveMoreComment(long commentId, long offset) {
        //1. 반환 객체 생성
        List<MoreCommentResponse> moreCommentResponseList = new ArrayList<>();
        //2. 대댓글 정보 가져오기
        List<CommentDetailFromDB> commentList = postRepository.getRecursiveMoreCommentList(commentId, offset);
        //3. 반환객체에 매핑
        // 3. 대댓글 데이터를 MoreCommentResponse로 변환하여 리스트에 추가
        for (CommentDetailFromDB comment : commentList) {
            MoreCommentResponse moreCommentResponse = new MoreCommentResponse();
            moreCommentResponse.settingResponse(comment);  // CommentDetailFromDB를 MoreCommentResponse에 매핑
            moreCommentResponseList.add(moreCommentResponse);  // 리스트에 추가
        }
        return moreCommentResponseList;
    }
    //recursive 방식 끝

}

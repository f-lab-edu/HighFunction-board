package com.main.board.post.repository;

import com.main.board.post.DTO.CommentDetailFromDB;
import com.main.board.post.DTO.CreatePostRequest;
import com.main.board.post.DTO.PostDetailFromDB;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostRepository {
    PostDetailFromDB getPostDetail(Long postId);
    //서브쿼리
    List<CommentDetailFromDB> getCommentList(long postId, long offset);
    List<CommentDetailFromDB> getMoreCommentList(long commentId, long offset);

    //JOIN
    List<CommentDetailFromDB> getJoinCommentList(long postId, long offset);
    List<CommentDetailFromDB> getJoinMoreCommentList(long commentId, long offset);

    //재귀 recursive
    List<CommentDetailFromDB> getRecursiveCommentList(long postId, long offset);
    List<CommentDetailFromDB> getRecursiveMoreCommentList(long commentId, long offset);

    //게시물 작성 ceate
    void createPost(CreatePostRequest createPostRequest);



}

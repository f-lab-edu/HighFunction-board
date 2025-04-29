package com.main.board.post.repository;

import com.main.board.post.DTO.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostRepository {
    PostDetailFromDB getPostDetail(Long postId);

    long findByPostId(long commentId);

    //게시물 이미지 가져오기
    List<ImageUrlFromDB> getImageUrlList(long postId);

    //서브쿼리
    List<CommentDetailFromDB> getCommentList(long postId, long offset);
    List<CommentDetailFromDB> getMoreCommentList(long commentId, long offset);

    //JOIN
    List<CommentDetailFromDB> getJoinCommentList(long postId, long offset);
    List<CommentDetailFromDB> getJoinMoreCommentList(long commentId, long offset);

    //재귀 recursive
    List<CommentDetailFromDB> getRecursiveCommentList(long postId, long offset);
    List<CommentDetailFromDB> getRecursiveMoreCommentList(long commentId, long offset, long postId);

    //게시물 작성 ceate
    void createPost(CreatePostRequest createPostRequest);
    //게시물 수정 update
    void updatePost(UpdatePostRequest updatePostRequest);
    //게시물 삭제 delete
    void deletePost(long postId);
    //해당 유저가 작성한 게시글인지 확인
    boolean selectPostForMember(long postId, long memberId);

    //유저가 작성한 최신 게시물ID(postId) 가져오기
    long selectRecentPostId(long memberId);
    //게시물 이미지 등록
    void createPostImage(@Param("postId") long postId, @Param("filePath") String filePath);



}

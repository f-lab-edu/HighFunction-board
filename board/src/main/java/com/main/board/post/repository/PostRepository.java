package com.main.board.post.repository;

import com.main.board.post.DTO.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

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

    //댓글 성능 튜닝
    //1. path가져오기
    long getPathByCommentId(long commentId);
    List<CommentDetailFromDB> findAllComment(long parentPath, long postId, long offset);

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

    //logical_Id 가져오기
    long getLogicalId(long postId);
    //logical_id 업데이트
    void updateLogicalId(long postId, long last_logical_id);
    //commentPath 가져오기
    String getCommentPath(long postId, long parentId);

    //댓글 작성(원댓글)
    void createParentComment(@Param("createCommentRequest") CreateCommentRequest createCommentRequest,@Param("last_logical_id") long last_logical_id ,@Param("postId") long postId);
    //댓글 작성(대댓글)
    void createChildComment(@Param("createCommentRequest") CreateCommentRequest createCommentRequest,@Param("last_logical_id") long last_logical_id,@Param("postId") long postId,@Param("commentPath") String commentPath);
}

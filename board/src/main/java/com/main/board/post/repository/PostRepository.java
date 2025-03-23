package com.main.board.post.repository;

import com.main.board.post.DTO.ChildCommentFromDB;
import com.main.board.post.DTO.CommentDetailFromDB;
import com.main.board.post.DTO.PostDetailFromDB;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostRepository {
    PostDetailFromDB getPostDetail(Long postId);
    List<CommentDetailFromDB> getCommentList(Long postId);
    List<ChildCommentFromDB> getChildCommentList(Long commentId);
}

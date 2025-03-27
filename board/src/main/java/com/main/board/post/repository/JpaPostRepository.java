package com.main.board.post.repository;

import com.main.board.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface JpaPostRepository extends JpaRepository<Post, Long>, CustomPostRepository{
    // 특정 게시물에 대한 상세 정보 가져오기

    @Query("SELECT p.postId AS postId, " +
            "p.title AS postTitle, " +
            "p.content AS postContent, " +
            "p.viewCount AS viewCount, " +
            "p.likeCount AS likeCount, " +
            "p.badCount AS badCount, " +
            "p.createdAt AS createdAt, " +
            "m.AuthorEmail AS authorEmail, " +
            "SUM(CASE WHEN c.parent IS NULL THEN 1 ELSE 0 END) AS commentCount " +
            "FROM Post p " +
            "JOIN p.member m " +
            "LEFT JOIN p.comments c " +
            "WHERE p.postId = :postId " +
            "GROUP BY p.postId")
    Object[] findPost(long postId);


}

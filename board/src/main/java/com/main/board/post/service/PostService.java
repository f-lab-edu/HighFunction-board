package com.main.board.post.service;

import com.main.board.post.DTO.CommentDetailFromDB;
import com.main.board.post.DTO.MoreCommentResponse;
import com.main.board.post.DTO.PostDetailFromDB;
import com.main.board.post.DTO.PostDetailResponse;
import com.main.board.post.entity.*;
import com.main.board.post.repository.JpaPostRepository;
import com.main.board.post.repository.PostRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    //Mybatis레포지토리
    private final PostRepository postRepository;

    //JPA레포지토리
    private final JpaPostRepository jpaPostRepository;

    //JPA사용
    private final JPAQueryFactory queryFactory;


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

    /*
    JPQL 사용 but 결과가 Object[] 배열형태로 담김
    엔티티로 받거나 DTOfromDB중 어떤걸로 받아야하는지 의문
    그리고 가공을 하는 작업을 어디서 하냐도 문제(repository에서 할지 service에서 할지)
    배열형태로 반환하기에 만들기 실패

    게시물 조회 작업중 실패 (댓글 조회 고로 미구현)
    */
    public PostResponse getJpaPostDetail(long postId, long offset) {

        // .orElseThrow(() -> new RuntimeException("게시글이 없습니다.")); 적용할려면?
        // 댓글 갯수가져오는것은 기본적인 CRUD문으로는 안됨
        Object[] postDetail = jpaPostRepository.findPost(postId);


        return PostResponse.builder()
                .build();
    }

    //QDSL 사용
    public PostResponse getQdslPostDetail(long postId, long offset) {

        QPost qPost = QPost.post;
        QMember qMember = QMember.member;
        QComment qComment = QComment.comment;

        BooleanExpression hasNoParent = qComment.parent.isNull(); // 부모가 없는 댓글만

        /*
        quertyFactory를 사용하였으나
        JpaPostRepository에 CustomPostRepository를 상속받고 구현체인 CustomPostRepositoryImpl에서
        queryFactory를 사용하여 구현하려고 하였으나 이방식과 quertyFactory사용 차이를 모르니 지금으로썬
        그냥 모르는 수준임
        */
        PostResponse postDetail = queryFactory.select(Projections.fields(PostResponse.class,
                qPost.postId.as("postId"),
                qPost.title.as("postTitle"),
                qPost.content.as("postContent"),
                qPost.viewCount.as("viewCount"),
                qPost.likeCount.as("likeCount"),
                qPost.badCount.as("badCount"),
                qPost.createdAt.as("createdAt"),
                qMember.AuthorEmail.as("authorEmail"),
                // 댓글의 부모 댓글 카운트 v1 실패 & Long타입이라 L을 붙여줌
                //qComment.id.count().when(qComment.parent.isNull()).then(1).otherwise(0).sum().as("hasChild")
                // v2버전 (when 조건을 CaseBuilder로 처리)
                new CaseBuilder().when(hasNoParent).then(1L).otherwise(0L).as("commentCount")
                ))
                .from(qPost)
                .join(qMember).on(qPost.member.id.eq(qMember.id))
                .leftJoin(qComment).on(qPost.postId.eq(qComment.post.postId))
                .where(qPost.postId.eq(postId))
                .groupBy(qPost.postId, qComment.parent) // ONLY_FULL_GROUP_BY 에러 parent_id 추가
                .fetchOne(); // 단건 조회
                //왜 다건으로 나오는지 모르겠음 NonUniqueResultException: Query did not return a unique result: 2 results were returned
                // queryDSL방법 여기서 실패

        if (postDetail == null) {
            throw new RuntimeException("게시글이 없습니다.");
        }

        return postDetail;
    }

}

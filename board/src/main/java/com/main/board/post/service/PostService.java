package com.main.board.post.service;

import com.main.board.post.DTO.*;
import com.main.board.post.repository.PostRepository;
import com.main.board.post.util.MoreCommentConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PostService {

    //Mybatis레포지토리
    private final PostRepository postRepository;

    //파일 업로드 경로
    private final String uploadDir = "C:\\Users\\uroeroe\\Desktop\\S3";



    //1. 서브쿼리방식
    @Transactional(readOnly = true)
    public PostDetailResponse getPostDetail(long postId, long offset) {

        //1. 게시글 정보 가져오기
        PostDetailFromDB postDetailList = postRepository.getPostDetail(postId);

        if(postDetailList.getCommentCount() == 0) {
            return new PostDetailResponse(postDetailList, null, null);
        }
        //2. 댓글 정보 가져오기
        List<CommentDetailFromDB> commentList = postRepository.getCommentList(postId, offset);
        //3.
        if(postDetailList.getHasImage() == 0) {
            return new PostDetailResponse(postDetailList, commentList, null);
        }

        List<ImageUrlFromDB> imageUrlList = postRepository.getImageUrlList(postId);

        return new PostDetailResponse(postDetailList, commentList, imageUrlList);
    }

    //대댓글 더보기 기능
    public List<MoreCommentResponse> getMoreComment(long commentId, long offset) {

        //1. 반환 객체 생성
        List<MoreCommentResponse> moreCommentResponseList = new ArrayList<>();
        //2. 대댓글 정보 가져오기
        List<CommentDetailFromDB> commentList = postRepository.getMoreCommentList(commentId, offset);

        //재귀를 써서 사용하는게 best같다 만약 recursive사용안하면 어떤식으로 구현해야할지 잘모르겠음
        for(int i = 0; i < commentList.size(); i++) {
            if(commentList.get(i).isHasChild()) {
                commentList.get(i).setChildCommentList(postRepository.getMoreCommentList(commentList.get(i).getCommentId(), offset));
            }
        }

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

        if(postDetailList.getCommentCount() == 0) {
            return new PostDetailResponse(postDetailList, null, null);
        }
        //2. 댓글 정보 가져오기
        List<CommentDetailFromDB> commentList = postRepository.getJoinCommentList(postId, offset);

        if(postDetailList.getHasImage() == 0) {
            return new PostDetailResponse(postDetailList, commentList, null);
        }

        List<ImageUrlFromDB> imageUrlList = postRepository.getImageUrlList(postId);

        return new PostDetailResponse(postDetailList, commentList, imageUrlList);
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

        if(postDetailList.getCommentCount() == 0) {
            return new PostDetailResponse(postDetailList, null, null);
        }
        //2. 댓글 정보 가져오기
        List<CommentDetailFromDB> commentList = postRepository.getRecursiveCommentList(postId, offset);

        if(postDetailList.getHasImage() == 0) {
            return new PostDetailResponse(postDetailList, commentList, null);
        }

        List<ImageUrlFromDB> imageUrlList = postRepository.getImageUrlList(postId);

        return new PostDetailResponse(postDetailList, commentList, imageUrlList);
    }

    public List<MoreCommentResponse> getRecursiveMoreComment(long commentId, long offset, long postId) {
        //1. 반환 객체 생성
        List<MoreCommentResponse> moreCommentResponseList = new ArrayList<>();
        //2. 대댓글 정보 가져오기
        List<CommentDetailFromDB> commentList = postRepository.getRecursiveMoreCommentList(commentId, offset, postId);

        Map<Long, CommentDetailFromDB> commentMap = new HashMap<>();


        // 3. 대댓글 데이터를 MoreCommentResponse로 변환하여 리스트에 추가
        for (CommentDetailFromDB comment : commentList) {
            commentMap.put(comment.getCommentId(), comment);
        }

        for(CommentDetailFromDB comment : commentList) {
            if(comment.getParentId() != null) {
                CommentDetailFromDB parent = commentMap.get(comment.getParentId());
                if(parent != null) {
                    parent.getChildCommentList().add(comment);
                }
                else {
                    MoreCommentResponse convertingData = new MoreCommentConverter(comment).toObject();
                    moreCommentResponseList.add(convertingData);
                }
            } else {
                MoreCommentResponse convertingData = new MoreCommentConverter(comment).toObject();
                moreCommentResponseList.add(convertingData);
            }
        }

        return moreCommentResponseList;
    }
    //recursive 방식 끝


    @Transactional
    public void createPost(CreatePostRequest createPostRequest) {
        if(createPostRequest.getPostImageList() == null) {
            postRepository.createPost(createPostRequest);
        }
        else {
            //파일 업로드 로직
            String filePath;
            try {
                //1. 게시물 등록
                postRepository.createPost(createPostRequest);
                //2. 파일 경로 DB에 저장하기위해 게시물 조회
                long recentlyPostId = postRepository.selectRecentPostId(createPostRequest.getMemberId());

                for(int i = 0; i < createPostRequest.getPostImageList().size(); i++) {
                    String fileName = UUID.randomUUID() + "_" + createPostRequest.getPostImageList().get(i).getOriginalFilename();
                    filePath = uploadDir + "\\" + fileName;

                    //1. 파일 업로드
                    createPostRequest.getPostImageList().get(i).transferTo(new java.io.File(filePath));
                    //2. DB에 파일 경로 저장
                    postRepository.createPostImage(recentlyPostId, filePath);
                }

            } catch (Exception e) {
                throw new RuntimeException("파일 업로드 실패", e);
            }
        }
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

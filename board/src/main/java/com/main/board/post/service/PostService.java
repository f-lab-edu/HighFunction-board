package com.main.board.post.service;

import com.main.board.post.DTO.*;
import com.main.board.post.repository.PostRepository;
import com.main.board.post.util.MoreCommentConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

        List<CommentDetailFromDB> childComments = findRoopComment(commentId, offset);

        //3. 자바에서 계층화를 위한 Map 생성(temp역할)
        Map<Long, CommentDetailFromDB> commentMap = new HashMap<>();

        // 3. 대댓글 데이터를 MoreCommentResponse로 변환하여 리스트에 추가
        for (CommentDetailFromDB comment : childComments) {
            // CommentDetailFromDB를 MoreCommentResponse에 매핑
            MoreCommentResponse moreCommentResponse = new MoreCommentResponse(comment);
            moreCommentResponseList.add(moreCommentResponse);  // 리스트에 추가
        }
        return moreCommentResponseList;
    }

    public List<CommentDetailFromDB> findRoopComment(long commentId, Long offset) {
        // 1단계 자식 댓글 가져오기
        List<CommentDetailFromDB> commentList = postRepository.getMoreCommentList(commentId, offset);

        List<MoreCommentResponse> result = new ArrayList<>();

        for(CommentDetailFromDB comment : commentList) {
            if (comment.isHasChild()) {
                List<CommentDetailFromDB> childComments = findRoopComment(comment.getCommentId(), offset);
                comment.setChildCommentList(childComments);
            }
        }

        return commentList;
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
        List<CommentDetailFromDB> childComments = findRoopComment(commentId, offset);

        // 3. 대댓글 데이터를 MoreCommentResponse로 변환하여 리스트에 추가
        for (CommentDetailFromDB comment : childComments) {
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
        //2. 대댓글 정보 (계층화X)
        List<CommentDetailFromDB> commentList = postRepository.getRecursiveMoreCommentList(commentId, offset, postId);
        //3. 자바에서 계층화를 위한 Map 생성(temp역할)
        Map<Long, CommentDetailFromDB> commentMap = new HashMap<>();


        // 4. 대댓글 데이터를 Map에 추가
        for (CommentDetailFromDB comment : commentList) {
            commentMap.put(comment.getCommentId(), comment);
        }

        //5. 대댓글 데이터를 계층화
        for(CommentDetailFromDB comment : commentList) {
            // 5-1. 부모 댓글이 있는 경우
            if(comment.getParentId() != null) {
                // 5-1-1.Map에서 해당 부모 댓글을 찾아 변수에 담아준다
                CommentDetailFromDB parent = commentMap.get(comment.getParentId());
                // 5-1-2. 부모 댓글이 null이 아닌 경우
                if(parent != null) {
                    // 5-1-3. 부모 댓글의 자식 댓글 리스트에 현재 댓글을 추가
                    parent.getChildCommentList().add(comment);
                }
                // 5-1-4. 부모 댓글(map에서꺼낸 parent)가 null인 경우 comment데이터가 최상위 부모이기에 반환리스트에 추가
                else {
                    MoreCommentResponse convertingData = new MoreCommentConverter(comment).toObject();
                    moreCommentResponseList.add(convertingData);
                }
            // 5-1-2. 부모댓글이 없는경우 이역시도 최상위 부모이기에 반환리스트에 추가
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
        // 이미지가 없으면 그냥 게시물만 등록하고 종료
        if (createPostRequest.getPostImageList() == null) {
            postRepository.createPost(createPostRequest);
            return;
        }
        try {
            // 1. 게시물 등록
            postRepository.createPost(createPostRequest);
            // 2. 등록된 게시물 ID 조회
            long recentlyPostId = postRepository.selectRecentPostId(createPostRequest.getMemberId());

            // 3. 이미지 파일 저장 및 경로 DB 저장
            /*
             1. for(int i = 0; i < createPostRequest.getPostImageList().size(); i++)
             위 인덱스 방식은 코드가 장황하고 IndexOutOfBoundsException이 있기에 변경
             2. createPostRequest.getPostImageList().get(i).getOriginalFilename();
             형식의 request에 직접 접근하는게 아닌 한번에 MultipartFile로 하나씩 꺼내 file을 바로 사용할수있도록 변경
             변수명이 명확해져 가독성 증가
            */
            for (MultipartFile file : createPostRequest.getPostImageList()) {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                String filePath = uploadDir + "\\" + fileName;

                file.transferTo(new java.io.File(filePath));
                postRepository.createPostImage(recentlyPostId, filePath);
            }
        } catch (Exception e) {
            throw new RuntimeException("파일 업로드 실패", e);
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //TODO : authentication.getName()으로 memberId를 가져오는 로직을 구현해야함
        long memberId = 1234; //authentication.getName()
        if (authentication != null && authentication.isAuthenticated()) {
            authentication.getName();  // 여기서 반환되는 값은 보통 유저의 ID
        }
        boolean checkOwner = postRepository.selectPostForMember(deletePostRequest.getPostId(), memberId);
        if(!checkOwner) {
            throw new IllegalArgumentException("게시물 작성자가 아닙니다.");
        }
        postRepository.deletePost(deletePostRequest);
    }
}

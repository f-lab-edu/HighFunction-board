package com.main.board.post.service;

import com.main.board.post.DTO.ChildCommentFromDB;
import com.main.board.post.DTO.CommentDetailFromDB;
import com.main.board.post.DTO.PostDetailFromDB;
import com.main.board.post.DTO.PostDetailResponse;
import com.main.board.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


    //게시글 1개 + 댓글 + 대댓글 != 대용량? 한번에 떄려도 문제 없을까?
    public PostDetailResponse getPostDetail(Long postId) {

        PostDetailResponse postDetailResponseList = new PostDetailResponse();

        PostDetailFromDB postDetailList = postRepository.getPostDetail(postId);

        List<CommentDetailFromDB> tempCommentList = new ArrayList<>();

        for(int i = 0; i < postDetailList.getCommentCount(); i++) {

            List<CommentDetailFromDB> commentList = postRepository.getCommentList(postId);
            List<ChildCommentFromDB> childCommentList = postRepository.getChildCommentList(commentList.get(i).getCommentId());
            commentList.get(i).setChildCommentList(childCommentList);
            tempCommentList.add(commentList.get(i));
        }

        postDetailResponseList.settingResponse(postDetailList, tempCommentList);



        return postDetailResponseList;
    }
}

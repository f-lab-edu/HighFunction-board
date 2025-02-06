package com.main.board.mainBoard.service;

import com.main.board.mainBoard.DTO.*;
import com.main.board.mainBoard.repository.MainBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainBoardService {

    private final MainBoardRepository mainBoardRepository;

    //단순조회니깐 트랜잭션은 걸지않아도 된다 라고 생각함
    public List<MainBoardPostResponse> getMainBoardForOffset(OffsetRequest offsetRequest) {
        List<MainBoardPostResponse> mainBoardPostResponsesList = mainBoardRepository.getMainBoardForOffset(offsetRequest);
        return mainBoardPostResponsesList;
    }

    public List<MainBoardPostResponse> getMainBoardForOffsetMK2(OffsetRequest offsetRequest) {
        //response를 쪼개면 Request도 쪼개야한다?
        List<PostResponse> postResponse = mainBoardRepository.getPost(offsetRequest);

        List <MainBoardPostResponse> mainBoardPostResponsesListMK2 = new ArrayList<>();

        for(PostResponse post : postResponse) {
            MainBoardPostResponse response = new MainBoardPostResponse();

            response.setPostId(post.getPostId());
            response.setPostTitle(post.getPostTitle());
            response.setViewCount(post.getViewCount());
            response.setLikeCount(post.getLikeCount());
            response.setBadCount(post.getBadCount());
            response.setCreatedAt(post.getCreatedAt());

            // 댓글 수 조회 (getCommentCountByPostId)
            CommentCountResponse commentCount = mainBoardRepository.getCommentCount(post.getPostId());
            response.setCommentCount(commentCount.getCommentCount());

            // 회원 이메일 조회 (getMemberByPostId)
            EmailResponse emailResponse = mainBoardRepository.getEmail(post.getPostId());
            response.setEmail(emailResponse.getEmail());

            // 결과 리스트에 추가
            mainBoardPostResponsesListMK2.add(response);
        }

        return mainBoardPostResponsesListMK2;
    }

    public List<MainBoardPostResponse> getMainBoardForCursor(CursorRequest cursorRequest) {
        List<MainBoardPostResponse> mainBoardPostResponsesList = mainBoardRepository.getMainBoardForCursor(cursorRequest);
        return mainBoardPostResponsesList;
    }
}

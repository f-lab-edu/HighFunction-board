package com.main.board.mainBoard.service;

import com.main.board.mainBoard.DTO.*;
import com.main.board.mainBoard.repository.MainBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainBoardService {

    private final MainBoardRepository mainBoardRepository;

    //단순조회니깐 트랜잭션은 걸지않아도 된다 라고 생각함
    public List<MainBoardPostResponse> getMainBoardForOffset(OffsetRequest offsetRequest) {
        List<DB_MainBoardData> postDbData = mainBoardRepository.getMainBoardForOffset(offsetRequest);

        List<MainBoardPostResponse> mainBoardPostResponsesList = new ArrayList<>();

        for (DB_MainBoardData data : postDbData) {
            mainBoardPostResponsesList.add(new MainBoardPostResponse(data)); // 변환 과정 필요
        }

        return mainBoardPostResponsesList;
    }

    /*
    @Transactional(readOnly = true)
    트랜잭션안에서 변경을 불가능하게함으로써 다른 트랜잭션에게 영향을 주지않게끔 dirty read를 방지한다
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    트랜잭션이 끝날때까지 같은 데이터를 읽도록 보장하여 repeatable read를 방지한다
    게시판 조회의 경우는 REPEATABLE_READ 설정을 안해줘도 될것같긴하다 조회시 변경이 되도 상관없기때문에
     */
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public List<MainBoardPostResponse> getMainBoardForOffsetMK2(OffsetRequest offsetRequest) {
        // 1. 게시물 조회하기(Post)
        List<PostResponse> postResponse = mainBoardRepository.getPost(offsetRequest);

        // 2.댓글과 이메일을 조회하기위한 게시물ID 리스트 추출
        List<Long> boardIdList = postResponse.stream()
                                 .map(PostResponse::getPostId)
                                 .toList(); // -> immutable하기에 .collect(Collectors.toList()); 대신 사용 (mutable해서 미사용)

        //3. 이메일, 댓글수 조회하기
        List<DB_CommentCount> commentCountOfDB = mainBoardRepository.getCommentCount(boardIdList);
        List<DB_Email> emailsOfDB = mainBoardRepository.getEmail(boardIdList);

        //4. 데이터를 삽입하기 위해서 Map은 get사용시 O(1)이기에 사용 (데이터가 많아지면 성능차이가 날수있음)
        Map<Long, Long> commentCountMap = commentCountOfDB.stream()
                .collect(Collectors.toMap(DB_CommentCount::getPostId, DB_CommentCount::getCommentCount));

        Map<Long, String> emailMap = emailsOfDB.stream()
                .collect(Collectors.toMap(DB_Email::getPostId, DB_Email::getEmail));

        //5. retrun 객체 생성
        List<MainBoardPostResponse> mainBoardPostResponsesListMK2 = new ArrayList<>();

        for(PostResponse post : postResponse) {
            MainBoardPostResponse response = new MainBoardPostResponse();

            response.setPostId(post.getPostId());
            response.setPostTitle(post.getPostTitle());
            response.setViewCount(post.getViewCount());
            response.setLikeCount(post.getLikeCount());
            response.setBadCount(post.getBadCount());
            response.setCreatedAt(post.getCreatedAt());

            // map이 아니였다면 list를 돌면서 찾아야함 -> O(n)
            response.setCommentCount(commentCountMap.getOrDefault(post.getPostId(), 0L));
            response.setEmail(emailMap.getOrDefault(post.getPostId(), "이메일 없음"));


            // 결과 리스트에 추가
            mainBoardPostResponsesListMK2.add(response);
        }

        return mainBoardPostResponsesListMK2;
    }

    public List<MainBoardPostResponse> getMainBoardForCursor(CursorRequest cursorRequest) {
        List<DB_MainBoardData> postDbData = mainBoardRepository.getMainBoardForCursor(cursorRequest);

        List<MainBoardPostResponse> mainBoardPostResponsesList = new ArrayList<>();

        /*
        1. 지금과 같이 for문 쓰기
        2. stream 사용하기
        추가적으로 List 크기 Limit값 만큼 사이즈 지정하기
        뭐가 효율적일것인가?
         */
        for (DB_MainBoardData data : postDbData) {
            System.out.println("Data class: " + data.getClass().getName());
            mainBoardPostResponsesList.add(new MainBoardPostResponse(data)); // 변환 과정 필요
        }

        return mainBoardPostResponsesList;
    }
}

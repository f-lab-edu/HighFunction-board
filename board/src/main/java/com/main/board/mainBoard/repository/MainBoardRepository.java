package com.main.board.mainBoard.repository;

import com.main.board.mainBoard.DTO.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MainBoardRepository {
    List<MainBoardPostResponse> getMainBoardForOffset(OffsetRequest offsetRequest);
    //버전2 쿼리 쪼개서 조합해보기
    List<PostResponse> getPost(OffsetRequest offsetRequest);
    EmailResponse getEmail(Long postId);
    CommentCountResponse getCommentCount(Long postId);


    List<MainBoardPostResponse> getMainBoardForCursor(CursorRequest cursorRequest);
}

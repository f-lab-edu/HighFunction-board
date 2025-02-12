package com.main.board.mainBoard.repository;

import com.main.board.mainBoard.DTO.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MainBoardRepository {
    // 서브쿼리는 지양하는게 맞다
    List<MainBoardDataFromDB> getMainBoardForOffset(OffsetRequest offsetRequest);
    //버전2 쿼리 쪼개서 조합해보기
    List<PostResponse> getPost(OffsetRequest offsetRequest);
    List<EmailFromDB> getEmail(List boardIdList);
    List<CommentCountFromDB> getCommentCount(List boardIdList);


    List<MainBoardDataFromDB> getMainBoardForCursor(CursorRequest cursorRequest);
}

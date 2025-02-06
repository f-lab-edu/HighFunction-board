package com.main.board.mainBoard.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor
public class CursorRequest {
    private Long limit;
    private Long cursorId; //커서값은 게시물 아이디
    private String keyword;
    private String sort;

    public CursorRequest(Long limit, Long cursorId, String keyword, String sort) {
        this.limit = limit;
        this.cursorId = cursorId;
        this.keyword = keyword;
        this.sort = sort;
    }
}

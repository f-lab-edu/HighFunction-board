package com.main.board.mainBoard.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor
public class CursorRequest {

    //1. Request객체안에 상수 선언하기
    public static final String DESC = "DESC";
    public static final String ASC = "ASC";

    public static CursorRequest defaultDesc() {
        return new CursorRequest(10L, null, null, DESC);
    }


    @NotNull(message = "limit은 필수값입니다.")
    private Long limit;
    private Long cursorId; //커서값은 게시물 아이디
    private String keyword;
    private String sort;

    public CursorRequest(Long limit, Long cursorId, String keyword, String sort) {
        this.limit = nullCheckLimit(limit);
        this.cursorId = cursorId;
        this.keyword = keyword;
        this.sort = validationSort(sort);
    }

    public Long nullCheckLimit(Long limit) {
        if (limit == null) {
            throw new IllegalArgumentException("limit은 필수값입니다.");
        }
        if(limit <1) {
            throw new IllegalArgumentException("limit은 1이상이어야 합니다.");
        }
        return limit;
    }

    public String validationSort(String sort) {
        String sortCheck = sort.trim().toUpperCase();
        if (sortCheck == null) {
            return "ASC";
        }
        if (sortCheck == "DESC") {
            return "DESC";
        }
        return sortCheck;
    }
}

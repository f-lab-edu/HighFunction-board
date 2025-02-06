package com.main.board.mainBoard.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OffsetRequest {
    @NotNull(message = "limit은 필수값입니다.")
    private Long limit; // 한 페이지당 가져올 데이터의 개수
    @NotNull(message = "page는 필수값입니다.")
    private Long page;
    private Long offset; // 페이지네이션 시작 위치 (예: (페이지번호 - 1) * 10)
    private String keyword;
    private String sort;


    public OffsetRequest(Long limit, Long page, Long offset, String keyword, String sort) {
    this.limit = nullCheckLimit(limit);
    this.page = nullCheckPage(page);
    this.offset = offset;
    this.keyword = keyword;
    this.sort = sort;
    }

    public Long nullCheckLimit(Long limit) {
        if (limit == null) {
            throw new IllegalArgumentException("limit은 필수값입니다.");
        }
        return limit;
    }

    public Long nullCheckPage(Long page) {
        if (page == null) {
            throw new IllegalArgumentException("page 필수값입니다.");
        }
        return page;
    }
}

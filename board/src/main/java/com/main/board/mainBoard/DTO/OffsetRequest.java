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


    public OffsetRequest(Long limit, Long page, String keyword, String sort) {
    this.limit = nullCheckLimit(limit);
    this.page = nullCheckPage(page);
    this.offset = (page != null && limit != null) ? (page - 1) * limit : 0;
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

    public Long nullCheckPage(Long page) {
        if (page == null) {
            throw new IllegalArgumentException("page 필수값입니다.");
        }
        if(page <1) {
            throw new IllegalArgumentException("page는 1이상이어야 합니다.");
        }
        return page;
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

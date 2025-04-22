package com.main.board.post.util;

public class PostUtilMethod {

    //매직넘버 처리 1,0은 허용을 할수있지만 그 이외 숫자는 무조건 매직넘버 처리하자
    static final int DEFAULT_PAGE = 10;

    public static long calculateOffset(int page) {
        long offset = getOrDefaultOffset(page);
        return offset;
    }

    public static long checkPage(int page) {
        if (page < 1) {
            throw new IllegalArgumentException("page 값은 1 이상이어야 합니다.");
        }
        return page;
    }

    //합친버전
    public static long calculateOffsetAndCheckPage(int page) {
        if (page < 1) {
            throw new IllegalArgumentException("page 값은 1 이상이어야 합니다.");
        }
        long offset = getOrDefaultOffset(page);

        return offset;
    }

    private static int getOrDefaultOffset(int page) {
        return isPositive(page) ? (page - 1) * DEFAULT_PAGE : 0;
    }

    // 음수 체크
    private static boolean isPositive(int page) {
        return page >= 0;
    }
}

package com.main.board.post.util;

public class PostUtilMethod {

    public static long calculateOffset(int page) {
        long offset = page != 0 ? (page - 1) * 10 : 0;
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
        long offset = page != 0 ? (page - 1) * 10 : 0;

        return offset;
    }
}

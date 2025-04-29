package com.main.board.post.util;

import com.main.board.post.DTO.CommentDetailFromDB;
import com.main.board.post.DTO.MoreCommentResponse;

public class MoreCommentConverter {

    private final CommentDetailFromDB commentDetailFromDB;

    public MoreCommentConverter(CommentDetailFromDB commentDetailFromDB) {
        this.commentDetailFromDB = commentDetailFromDB;
    }

    public MoreCommentResponse toObject() {
        MoreCommentResponse moreCommentResponse = new MoreCommentResponse();
        moreCommentResponse.setCommentId(commentDetailFromDB.getCommentId());
        moreCommentResponse.setCommentContent(commentDetailFromDB.getCommentContent());
        moreCommentResponse.setLikeCount(commentDetailFromDB.getLikeCount());
        moreCommentResponse.setCreatedAt(commentDetailFromDB.getCreatedAt());
        moreCommentResponse.setAuthorEmail(commentDetailFromDB.getEmail());
        moreCommentResponse.setParentId(commentDetailFromDB.getParentId());
        moreCommentResponse.setHasChild(commentDetailFromDB.isHasChild());
        moreCommentResponse.setChildCommentList(commentDetailFromDB.getChildCommentList());

        return moreCommentResponse;
    }
}

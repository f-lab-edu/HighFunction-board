package com.main.board.post.search;

import com.main.board.post.entity.PostResponseFromDB;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDocument { // Elasticsearch 사용을위한 검색 필드

    private String postId; // ES에서는 String으로 통일
    private String postTitle;
    private String postContent;
    private String authorEmail;
    private String createdAt; // ISO 8601 문자열
    private Long viewCount;
    private Long likeCount;
    private Long badCount;

    public static PostDocument from(PostResponseFromDB post) {
        PostDocument doc = new PostDocument();
        doc.setPostId(String.valueOf(post.getPostId()));
        doc.setPostTitle(post.getPostTitle());
        doc.setPostContent(post.getPostContent());
        doc.setAuthorEmail(post.getAuthorEmail());
        doc.setCreatedAt(post.getCreatedAt().toString()); // LocalDateTime → ISO 문자열
        doc.setViewCount(post.getViewCount());
        doc.setLikeCount(post.getLikeCount());
        doc.setBadCount(post.getBadCount());
        return doc;
    }

}

package com.main.board.post.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long postId;

    @ManyToOne
    @JoinColumn(name = "member_id") // `member_id`는 외래 키
    private Member member; // 하나의 Member에 속함

    private String title;

    private String content;


    @Column(name = "viewcount")
    private Long viewCount;

    @Column(name = "like_count")
    private Long likeCount;

    @Column(name = "bad_count")
    private Long badCount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> comments; // 여러 개의 Comment를 가질 수 있음


}

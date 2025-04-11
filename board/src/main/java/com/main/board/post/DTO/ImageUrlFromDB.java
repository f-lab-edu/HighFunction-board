package com.main.board.post.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ImageUrlFromDB {
    private String imageUrl;
    private LocalDateTime createdAt;

    public ImageUrlFromDB(String imageUrl, LocalDateTime createdAt) {
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }
}

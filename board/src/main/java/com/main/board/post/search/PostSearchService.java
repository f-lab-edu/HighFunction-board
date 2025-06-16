package com.main.board.post.search;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostSearchService {
    private final PostSearchRepository postSearchRepository;

    public List<PostDocument> searchPosts(String keyword) throws IOException {
        return postSearchRepository.searchByKeyword(keyword);
    }
}
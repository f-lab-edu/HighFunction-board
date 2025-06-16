package com.main.board.post.search;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/search/posts")
@RequiredArgsConstructor
public class PostSearchController {

    private final PostSearchService postSearchService;

    @GetMapping
    public List<PostDocument> search(@RequestParam String keyword) throws IOException {
        return postSearchService.searchPosts(keyword);
    }
}
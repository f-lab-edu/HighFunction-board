package com.main.board.post.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import co.elastic.clients.elasticsearch.core.search.Hit;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PostSearchRepository {

    private final ElasticsearchClient client;

    /*
    Elasticsearch Java Client(공식 8.x 클라이언트)는 반드시 빌더 또는 람다를 사용.
    → 내부 구조가 **타입 세이프한 DSL(도메인 특화 언어)**로 설계되어있음
    → 문자열로 쿼리 작성하는 방식은 지원하지 X
    */
    public List<PostDocument> searchByKeyword(String keyword) throws IOException {
        // 1. MultiMatchQuery 객체 생성
        MultiMatchQuery multiMatchQuery = new MultiMatchQuery.Builder()
                .fields("postTitle", "postContent")  // 검색 대상 필드
                .query(keyword)                      // 검색어
                .build();

        // 2. Query 객체 생성
        Query query = new Query.Builder()
                .multiMatch(multiMatchQuery)
                .build();

        // 3. SearchRequest 객체 생성
        SearchRequest request = new SearchRequest.Builder()
                .index("posts")                      // 검색 대상 인덱스
                .query(query)
                .build();

        // 4. ElasticsearchClient 호출
        SearchResponse<PostDocument> response = client.search(request, PostDocument.class);

        // 5. 결과 처리
        return response.hits().hits().stream()
                .map(Hit::source)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
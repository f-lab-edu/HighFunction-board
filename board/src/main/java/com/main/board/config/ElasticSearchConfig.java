package com.main.board.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {

    public static ElasticsearchClient createClient() {
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200) //같은서버에 Elasticsearch가 설치되어있다면 localhost:9200
        ).build();

        /*
        RestClientTransport: 실제 HTTP 통신을 수행하는 전송 계층 객체
        JacksonJsonpMapper: 자바 객체 ↔ JSON 변환을 해주는 직렬화/역직렬화 처리기
        */
        return new ElasticsearchClient(
                new RestClientTransport(restClient, new JacksonJsonpMapper()) //통신을 위한 직렬화 역직렬화 처리
        );
    }

}

package com.example.searchtest.search;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {
    private String apiURL = "https://dapi.kakao.com/v3/search/book";

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void bookSearch() {


        String kakaoAk = "KakaoAK dfdbd13327e9bb8348b4b5e2466cb4d8";
        System.out.println(kakaoAk);
        RestTemplate rt = new RestTemplate();
        String url = apiURL + "?target=title&query=미움받을 용기";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization",kakaoAk);
        ResponseEntity<String> responseEntity =
                rt.exchange(url,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        String.class);

        System.out.println(responseEntity.getBody());

        HttpHeaders headers1 = new HttpHeaders();
        headers1.setContentType(MediaType.APPLICATION_JSON);
        headers1.add("X-Naver-Client-Id","jxfHLjQTMfDnLPvyuyfn");
        headers1.add("X-Naver-Client-Secret","dv0X27Gr1j");
        url = "https://openapi.naver.com/v1/search/book.json"+ "?query=미움";
        ResponseEntity<Map> responseEntity1 =
                rt.exchange(url,
                        HttpMethod.GET,
                        new HttpEntity<>(headers1),
                        Map.class);
       // System.out.println(responseEntity1.getBody());
    }
}
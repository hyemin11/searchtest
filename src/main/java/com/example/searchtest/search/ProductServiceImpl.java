package com.book.booksearch.search;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ProductServiceImpl implements ProductService {

    private String apiURL = "https://dapi.kakao.com/v3/search/book";

    @Value("${kakao-admin-key}")
    private String kakao_admin_key;

    @Override
    public String bookSearch(String searchWord, String target) throws Exception
    {
        String kakaoAk = "KakaoAK "+kakao_admin_key;
        RestTemplate rt = new RestTemplate();
        String url = apiURL + "?target="+target+"&query="+searchWord;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authoriaztion",kakaoAk );
        ResponseEntity<String> responseEntity =
                rt.exchange(url,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        String.class);

        return responseEntity.getBody();

    }
}

package com.example.searchtest.search;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RestAPITest {
    private String apiURL = "https://dapi.kakao.com/v3/search/book";
    private String naver = "https://openapi.naver.com/v1/search/book.json";

    @Value("${restapi.appkey.kakao}")
    private String kakao_admin_key;

    @Value("${naver.client.id}")
    private String naverId;

    @Value("${naver.client.secret}")
    private String naverSecret;

    @GetMapping("")
    @Test
    public HashMap<String, Object> callAPITest()
    {
        HashMap<String,Object> resultMap = new HashMap<>();

        try
        {
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(5000);
            factory.setReadTimeout(5000);

            CloseableHttpClient httpClient = HttpClientBuilder.create()
                    .setMaxConnTotal(50)
                    .setMaxConnPerRoute(20).build();

            factory.setHttpClient(httpClient);

            RestTemplate restTemplate = new RestTemplate(factory);

            HttpHeaders header = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<String>(header);

            String url = apiURL;
            UriComponents uri = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("Authorization","KakaoAK "+kakao_admin_key).build(false  );
            ResponseEntity<Map> response = restTemplate.exchange(uri.toString(), HttpMethod.GET,entity,Map.class);
            resultMap.put("statusCode",response.getStatusCodeValue());
            resultMap.put("header",response.getHeaders());
            resultMap.put("body",response.getBody());

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return resultMap;

    }
}

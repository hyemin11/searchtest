package com.example.searchtest.search;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
@Component
public class ProductServiceImpl implements ProductService {

    private String apiURL = "https://dapi.kakao.com/v3/search/book";
    private String naver = "https://openapi.naver.com/v1/search/book.json";
    @Value("${restapi.appkey.kakao}")
    private String kakao_admin_key;

    @Value("${naver.client.id}")
    private String naverId;

    @Value("${naver.client.secret}")
    private String naverSecret;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    SearchStatsRepository searchStatsRepository;

    @Autowired
    DocumentsRepository documentsRepository;


    @Override
    public HashMap<String,Object> callAPITest(Book book) throws Exception
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
            header.set("Authorization","KakaoAK "+kakao_admin_key);
            header.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(header);

            String target = book.getTarget();
            String searchWord = book.getSearchWord();
            String url = apiURL+"?target="+target+"&query="+searchWord;
            System.out.println("url="+url);

            UriComponents uri = UriComponentsBuilder.fromHttpUrl(apiURL)
                    .queryParamIfPresent("target", Optional.ofNullable(target))
                    .queryParam("query",searchWord)
                    .build(false  );

            ResponseEntity<Map> response = restTemplate.exchange(uri.toString(), HttpMethod.GET,entity,Map.class);
            //resultMap.put("statusCode",response.getStatusCodeValue());

            resultMap.put("header",response.getHeaders());
            resultMap.put("body",response.getBody());
            resultMap.put("documents",response.getBody().get("documents"));
            System.out.println(resultMap.get("documents"));
            JSONObject jsonObject = new JSONObject(resultMap.get("documents").toString());
            resultMap.put("json",jsonObject);

        } catch (Exception e) /** 카카오 불량시 네이버로 시도*/
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
            header.set("X-Naver-Client-Id",naverId);
            header.set("X-Naver-Client-Secret",naverSecret);
            HttpEntity<String> entity = new HttpEntity<String>(header);
            String searchWord = book.getSearchWord();
            UriComponents uri = UriComponentsBuilder.fromHttpUrl(naver)
                    .queryParam("query",searchWord)
                    .build(false  );
            ResponseEntity<Map> response = restTemplate.exchange(uri.toString(), HttpMethod.GET,entity,Map.class);
           // resultMap.put("statusCode",response.getStatusCodeValue());
            resultMap.put("header",response.getHeaders());
            resultMap.put("body",response.getBody());
            resultMap.put("documents",(Map)response.getBody().get("item"));

        }

        return resultMap;

    }
    @Override
    public HashMap<String, Object> callAPI(Book book) throws Exception
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
            header.set("Authorization","KakaoAK "+kakao_admin_key);
            HttpEntity<String> entity = new HttpEntity<String>(header);
            String target = book.getTarget();
            String searchWord = book.getSearchWord();
            String url = apiURL+"?target="+target+"&query="+searchWord;
            System.out.println("url="+url);

            UriComponents uri = UriComponentsBuilder.fromHttpUrl(apiURL)
                        .queryParamIfPresent("target", Optional.ofNullable(target))
                        .queryParam("query",searchWord)
                        .build(false  );

            ResponseEntity<Map> response = restTemplate.exchange(uri.toString(), HttpMethod.GET,entity,Map.class);
            resultMap.put("statusCode",response.getStatusCodeValue());
            resultMap.put("header",response.getHeaders());
            resultMap.put("body",response.getBody());
            resultMap.put("documents",(Map)response.getBody().get("documents"));


        } catch (Exception e) /** 카카오 불량시 네이버로 시도*/
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
            header.set("X-Naver-Client-Id",naverId);
            header.set("X-Naver-Client-Secret",naverSecret);
            HttpEntity<String> entity = new HttpEntity<String>(header);
            String searchWord = book.getSearchWord();
            UriComponents uri = UriComponentsBuilder.fromHttpUrl(naver)
                    .queryParam("query",searchWord)
                    .build(false  );
            ResponseEntity<Map> response = restTemplate.exchange(uri.toString(), HttpMethod.GET,entity,Map.class);
            resultMap.put("statusCode",response.getStatusCodeValue());
            resultMap.put("header",response.getHeaders());
            resultMap.put("body",response.getBody());
            resultMap.put("documents",response.getBody().entrySet());
        }

        return resultMap;

    }


    @Override
    public void saveKeyword(Book book) throws Exception{
        SearchStats stats = new SearchStats();
        if(searchStatsRepository.findById(book.getSearchWord()).isEmpty())
        {
            stats.setKeyword(book.getSearchWord());
            stats.setCount(1);
            searchStatsRepository.save(stats);
        }
        else
        {
            stats = searchStatsRepository.findById(book.getSearchWord()).get();
            stats.setCount(stats.getCount()+1);
            searchStatsRepository.save(stats);
        }

    }



}

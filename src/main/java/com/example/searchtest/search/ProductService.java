package com.example.searchtest.search;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public interface ProductService {

    void saveKeyword(Book book) throws Exception;

    HashMap<String, Object> callAPITest(Book book) throws Exception;

    HashMap<String, Object> callAPI(Book book) throws Exception;
}

package com.example.searchtest.search;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {
    @Resource
    ProductServiceImpl productService;

    @Test
    void selectSearch() {
    }

    @Test
    void resultSearch() throws Exception {

        // model.addAttribute("target","title");
        // model.addAttribute("searchWord","미움");
        //Map doc = productService.bookSearch("미움","title");
        //System.out.println(doc);

        //model.addAttribute("test",doc);
        //return doc;
    }
}
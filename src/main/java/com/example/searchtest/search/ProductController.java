package com.book.booksearch.search;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Controller
public class ProductController {
    private String apiURL = "https://dapi.kakao.com/v3/search/book";

    @Value("${kakao-admin-key}")
    private String kakao_admin_key;

    @Resource
    private ProductService productService;

    @GetMapping("/search")
    public String selectSearch(Model model,@RequestParam String target,@RequestParam String searchWord) throws Exception {
        model.addAttribute("target",target);
        model.addAttribute("searchWord",searchWord);
        String test = productService.bookSearch(searchWord,target);

        return test;
    }
/*
    @PostMapping("/result")
    public ModelAndView searchResult(@ModelAttribute Model model)
    {


        return "search";
    }*/








}

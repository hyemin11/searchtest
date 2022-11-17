package com.example.searchtest.search;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private DocumentsRepository documentsRepository;

    @RequestMapping("/result")
    public String selectSearch(Model model, @ModelAttribute("target") String target, @ModelAttribute("searchWord") String searchWord, HttpSession session) throws Exception {

        model.addAttribute("target",target);
        model.addAttribute("searchWord",searchWord);
        return "result";
    }

    @GetMapping("/search.do")
    public String resultSearch(Model model,
                                     @ModelAttribute("chk") Book book,
                                     HttpSession session) throws Exception {

        HashMap<String,Object> map = productService.callAPITest(book);
        Book sk = new Book();
        sk.setSearchWord(book.getSearchWord());
        productService.saveKeyword(sk);
        //JSONObject object = (JSONObject) map.get("json");
        model.addAttribute("doc",map.get("documents") );

        return "result";
    }

    @GetMapping("/searchresult.do")
    public String resultList(Model model, @ModelAttribute("doc") JSONObject object) throws Exception{


        return "search";
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String,Object>> getResult(){
        List<Documents> documents = documentsRepository.findAll();
        Map<String,Object> result = new HashMap<>();
        result.put("documents",documents);
        result.put("count",documents.size());
        return ResponseEntity.ok().body(result);

    }







}

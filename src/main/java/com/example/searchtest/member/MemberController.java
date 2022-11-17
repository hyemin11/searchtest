package com.example.searchtest.member;

import com.example.searchtest.search.Book;
import com.example.searchtest.search.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @Resource(name="MemberService")
    private MemberService memberService;

    // 회원가입 폼
    @GetMapping("/signup")
    public String signup(Model model)
    {
        model.addAttribute("memberDto", new Member());
        return "signup";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public RedirectView insertMember(@ModelAttribute Member memberDto, Model model) throws Exception
    {
        model.addAttribute("memberDto",memberDto);
        memberService.insertMember(memberDto);
        return new RedirectView("/");
    }

    @GetMapping("/")
    public String login(Model model)
    {
        model.addAttribute("signForm", new Member());
        return "index";
    }

    // 로그인
    @PostMapping("/search")
    public String loginAction(@ModelAttribute Member member, Model model, HttpSession session)
    throws Exception
    {
        Member mb = memberService.actionLogin(member);
        String result = "";
        if(!mb.equals(""))
        {
            result = "search";
        }
        else if(mb.equals(""))
        {
            result = "error";
        }
        Book book = new Book();
        model.addAttribute("chk",book);
        //model.addAttribute("target",target);
        //model.addAttribute("searchWord",searchWord);

        return result;
    }






}

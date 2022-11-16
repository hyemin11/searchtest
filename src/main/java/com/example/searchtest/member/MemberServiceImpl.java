package com.book.booksearch.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("MemberService")
public class MemberServiceImpl implements MemberService{

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void insertMember(Member member) throws Exception
    {
        // id 중복체크기


        // 비밀번호 암호
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
    }

    @Override
    public Member actionLogin(Member member) throws Exception
    {
        //1.입력한 비밀번호를 암호화 한다.
        String enpwd = passwordEncoder.encode(member.getPassword());
        member.setPassword(enpwd);

        //2. 아이디와 암호화된 비밀번호가 DB와 일치하는지 확인
        Member mb = memberRepository.getReferenceById(member.getId());
        if(mb.getPassword()==enpwd && !mb.getPassword().equals(""))
        {
            return member;
        }
        else
        {
            member = new Member();
        }

        return member;
    }

}

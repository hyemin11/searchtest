package com.example.searchtest.member;

public interface MemberService {

    public void insertMember(Member member) throws Exception;

    public Member actionLogin(Member mb) throws Exception;
}

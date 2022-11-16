package com.book.booksearch.member;

public interface MemberService {

    public void insertMember(Member member) throws Exception;

    public Member actionLogin(Member mb) throws Exception;
}

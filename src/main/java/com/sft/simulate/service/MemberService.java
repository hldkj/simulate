package com.sft.simulate.service;

import com.sft.simulate.entity.Member;
import com.sft.simulate.enums.error.UserError;
import com.sft.simulate.exceptions.ServiceException;
import com.sft.simulate.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Iterator<Member> getMemberList(){
        return memberRepository.findAll().iterator();
    }

    public void saveAll(List<Member> members) {
        memberRepository.saveAll(members);
    }

    public Member getMemberById(Long id){
        return memberRepository.findById(id).orElseThrow(()-> new ServiceException(UserError.NOT_FOUND));
    }

    public void updateCookieById(String cookie,Long Id){
        memberRepository.updateCookieById(cookie,Id);
    }

}

package com.sft.simulate.service;

import com.sft.simulate.entity.Member;
import com.sft.simulate.enums.error.UserError;
import com.sft.simulate.exceptions.ServiceException;
import com.sft.simulate.pojo.query.member.MemberQuery;
import com.sft.simulate.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Iterator<Member> getMemberList(){
        return memberRepository.findAll().iterator();
    }

    @Transactional
    public void saveAll(List<Member> members) {
        memberRepository.saveAll(members);
    }

    @Transactional(readOnly = true)
    public Member getMemberById(Long id){
        return memberRepository.findById(id).orElseThrow(()-> new ServiceException(UserError.NOT_FOUND));
    }

    public void updateCookieById(String cookie,Long Id){
        memberRepository.updateCookieById(cookie,Id);
    }

    @Transactional(readOnly = true)
    public Page<Member> members(MemberQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getSize());
        Page<Member> pages = memberRepository.findAll((Specification<Member>) (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!StringUtils.isEmpty(query.getName())) {
                predicates.add(cb.like(root.get("name").as(String.class), "%" + query.getName() + "%"));
            }
            Predicate[] query1 = new Predicate[predicates.size()];
            return cb.and(predicates.toArray(query1));
        }, pageable);
        return pages;
    }

    @Transactional(readOnly = true)
    public int findMixId(){
        return memberRepository.findTopByMemberId();
    }

}

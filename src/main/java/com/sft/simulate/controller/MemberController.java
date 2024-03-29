package com.sft.simulate.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sft.simulate.entity.Member;
import com.sft.simulate.enums.common.ResponseEnum;
import com.sft.simulate.pojo.MemberRequest;
import com.sft.simulate.pojo.Response;
import com.sft.simulate.pojo.query.member.MemberQuery;
import com.sft.simulate.service.MemberService;
import com.sft.simulate.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import java.util.*;

import static com.sft.simulate.pojo.CurrencyConstant.ENCODING;
import static com.sft.simulate.pojo.CurrencyConstant.MEMBER_LOGIN_URL;
import static com.sft.simulate.pojo.CurrencyConstant.PULLOUT_MEMBER_URL;

@RestController
@RequestMapping("/member")
@Slf4j
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Value("${host.b2c}")
    private String HOST;


    /**
     * 获取会员数据
     * @return
     */
    @RequestMapping("/pull")
    public Response pullOutMemberInfo(){
        int addNumber;
        //拉取会员数据
        try {
            String result = HttpClientUtil.get(HOST+PULLOUT_MEMBER_URL,null,ENCODING);
            Response response = JSONObject.parseObject(result,Response.class);
            if(response.getCode()!= ResponseEnum.SUCCESS.getCode()){
                return Response.fail(response.getMsg());
            }
            List<MemberRequest> results = JSONArray.parseArray
            (JSON.toJSONString(response.getData()),MemberRequest.class);
            //addNumber = saveMember(results);
            Iterator<Member> it = memberService.getMemberList();
            saveMember(it,results);
        } catch (Exception e) {
            log.error("系统异常:{}",e);
            return Response.fail("系统异常");
        }
        //return Response.success(addNumber);
        return Response.success();
    }


    /*private int saveMember(List<MemberRequest> requests){
        int addNumber = 0;
        List<Member> members = new ArrayList<>();
        int memberMix = memberService.findMixId();
        for(MemberRequest mr : requests){
            if(mr.getMember_id()<memberMix){
                continue;
            }
            Member member = new Member();
            member.setMemberId(mr.getMember_id());
            member.setUsername(mr.getUname());
            member.setPassword(mr.getPassword());
            member.setName(mr.getName());
            member.setMobile(mr.getMobile());
            member.setCookie(mr.getCookie());
            member.setCreateTime(new Date());
            members.add(member);
            addNumber+= addNumber;
        }
        memberService.saveAll(members);
        return addNumber;
    }*/



    private void saveMember(Iterator<Member> it,List<MemberRequest> results){
        List<Member> members = new ArrayList<>();
        for(MemberRequest mr : results){
            while (it.hasNext()){
                Member mb = it.next();
                if(StringUtils.equals(mr.getMember_id(),mb.getId())){
                    continue;
                }
            }
            Member member = new Member();
            member.setMemberId(mr.getMember_id());
            member.setUsername(mr.getUname());
            member.setPassword(mr.getPassword());
            member.setName(mr.getName());
            member.setMobile(mr.getMobile());
            member.setCookie(mr.getCookie());
            member.setCreateTime(new Date());
            members.add(member);

        }
        memberService.saveAll(members);
    }


    @RequestMapping("/login")
    public Response memberLogin(Long id){
        Member member = memberService.getMemberById(id);
        if(member==null){
            return Response.fail("无该用户");
        }
        Map<String,String> map = new HashMap<>();
        map.put("memberId",String.valueOf(member.getId()));
        String result = HttpClientUtil.post(HOST+MEMBER_LOGIN_URL,map,ENCODING);
        Response response = JSONObject.parseObject(result,Response.class);
        if(response.getCode()!=ResponseEnum.SUCCESS.getCode()){
            return Response.fail(response.getMsg());
        }
        String cookie = String.valueOf(response.getData());
        if(StringUtils.isEmpty(cookie)){
            return Response.fail("登录失败");
        }
        memberService.updateCookieById(cookie,id);
        return Response.success();
    }

    @GetMapping("/page")
    public Response<Page<Member>> query(MemberQuery query) {
        return Response.success(memberService.members(query));
    }



}

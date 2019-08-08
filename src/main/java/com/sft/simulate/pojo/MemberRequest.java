package com.sft.simulate.pojo;


import lombok.Data;

@Data
public class MemberRequest {

    private Long member_id;

    private String uname;

    private String password;

    private String name;

    private String cookie;

    private String mobile;

}

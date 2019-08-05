package com.sft.simulate.warp.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Created by yuyidi on 2019/3/20.
 * @desc
 */
@Data
@AllArgsConstructor
public class UserInfo {

    private String name;
    private String token;
    private Collection<GrantedAuthority> roles;
}

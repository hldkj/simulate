package com.sft.simulate.service;


import com.sft.simulate.entity.Role;
import com.sft.simulate.entity.User;
import com.sft.simulate.enums.error.UserError;
import com.sft.simulate.exceptions.ServiceException;
import com.sft.simulate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by yuyidi on 2019-06-27.
 * @desc
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository repository;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = repository.findByUsername(userName).orElseThrow(()-> new ServiceException(UserError.NOT_FOUND));
        List<GrantedAuthority> auths = new ArrayList<>();
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            auths.add(new SimpleGrantedAuthority("ROLE_" + role.getCode()));
        }
        org.springframework.security.core.userdetails.User auth =
                new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                auths);
        return auth;
    }


}

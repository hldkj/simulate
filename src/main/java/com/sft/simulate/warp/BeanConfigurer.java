package com.sft.simulate.warp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.sft.simulate.commants.CommonConstatnt;
import com.sft.simulate.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by yuyidi on 2019-04-16.
 * @desc
 */
@Configuration
@Slf4j
public class BeanConfigurer {

    Map<String, PasswordEncoder> encoders = new HashMap<>();

    {
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
    }

    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new DelegatingPasswordEncoder("bcrypt", encoders);
    }


    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        //设置日期格式
        ObjectMapper mapper = converter.getObjectMapper();
        Hibernate5Module hibernate5Module = new Hibernate5Module();
        mapper.registerModule(hibernate5Module);
        mapper.registerModule(new ParameterNamesModule());
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new SimpleDateFormat(CommonConstatnt.DATE_TIME_FORMAT));
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return converter;
    }

    @Bean
    UserDetailsService userDetailsService(UserService userService) {
        return userService;
    }

}


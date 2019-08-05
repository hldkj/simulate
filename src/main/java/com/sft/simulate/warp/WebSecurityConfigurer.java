package com.sft.simulate.warp;


import com.sft.simulate.warp.auth.hanlder.LoginFailureHandler;
import com.sft.simulate.warp.auth.hanlder.LoginSuccessHandler;
import com.sft.simulate.warp.auth.hanlder.LogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Created by yuyidi on 2019-04-23.
 * @desc
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_ENTRY_POINT = "/login";

    @Autowired
    @Qualifier("passwordEncoder")
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LoginSuccessHandler successHandler;
    @Autowired
    private LoginFailureHandler failureHandler;
    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailService;

    /**
     * 配置通过拦截器请求
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().headers().frameOptions().sameOrigin().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, LOGIN_ENTRY_POINT).permitAll()
                .antMatchers(HttpMethod.POST, "/register").permitAll()
                .anyRequest().authenticated()
                .and().cors()
                .and().formLogin().loginPage(LOGIN_ENTRY_POINT).successHandler(successHandler).failureHandler(failureHandler)
                .and().logout().invalidateHttpSession(true).logoutUrl("/logout")
                .logoutSuccessHandler(new LogoutSuccessHandler())
        ;

    }

    /**
     * 配置 Security Filter 链
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/fonts/**", "/images/**", "/js/**", "/lib/**", "/favicon.ico");
    }

    /**
     * 验证过程 若多个authenticationProvider 返回null 则交给下一个provider验证
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
        auth.eraseCredentials(false);
    }

}

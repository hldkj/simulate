package com.sft.simulate.warp.auth.hanlder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Created by yuyidi on 2017/7/20.
 * @desc
 */
@Slf4j
public class LogoutSuccessHandler implements org.springframework.security.web.authentication
        .logout.LogoutSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication
            authentication) throws IOException, ServletException {
        log.info("成功登出");
        redirectStrategy.sendRedirect(request,response,"/login");
    }
}

package com.sft.simulate.warp.auth.hanlder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***
 * @author yuyidi 2019-03-06 17:09:45
 * @class com.slianyun.security.warp.auth.handler.LoginFailureHandler
 * @description 登陆失败记录
 */
@Component
@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        log.error("登陆验证失败:错误原因：{}", exception);
        request.getSession().setAttribute("error", exception.getMessage());
        redirectStrategy.sendRedirect(request, response, "/login");

    }
}

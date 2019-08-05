package com.sft.simulate.warp.auth.hanlder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Created by yuyidi on 2019/3/7.
 * @desc
 */
@Component
@Slf4j
public class EntryPointHandler implements AuthenticationEntryPoint {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {
        log.warn("请求被禁止{}", request.getRequestURI());
        redirectStrategy.sendRedirect(request, response, "/403");
    }
}

package com.sft.simulate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Created by yuyidi on 2019-08-05.
 * @desc
 */
@Controller
public class SystemController {

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @GetMapping("/welcome")
    public ModelAndView welcome() {
        return new ModelAndView("welcome");
    }

    @GetMapping("/member/index")
    public ModelAndView member() {
        return new ModelAndView("/member/index");
    }


    @GetMapping("/goods/index")
    public ModelAndView goods() {
        return new ModelAndView("/goods/index");
    }


    @GetMapping("/pay/index")
    public ModelAndView pay() {
        return new ModelAndView("/pay/index");
    }

}

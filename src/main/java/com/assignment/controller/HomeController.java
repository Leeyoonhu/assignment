package com.assignment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class HomeController {

    ModelAndView mav = new ModelAndView();

    @GetMapping(value = "/")
    public ModelAndView getMain(){
        mav.setViewName("main");
        return mav;
    }

    @GetMapping("/join")
    public ModelAndView getJoin() {
        mav.setViewName("join");
        return mav;
    }

    @GetMapping("/login")
    public ModelAndView getLogin(){
        mav.setViewName("login");
        return mav;
    }
}

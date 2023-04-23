package com.assignment.controller;

import com.assignment.domain.Reserve;
import com.assignment.domain.User;
import com.assignment.repository.ReserveRepository;
import com.assignment.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
public class HomeController {

    ModelAndView mav = new ModelAndView();

    String userId;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ReserveRepository reserveRepository;

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

    @GetMapping("/reserve/{hospName}")
    public ModelAndView getReserve(@PathVariable String hospName, HttpSession session){
        mav.setViewName("reserve");
        mav.addObject("hospName", hospName);
        userId = (String) session.getAttribute("userId");
        User user = userRepository.findById(userId);
        mav.addObject("name", user.getName());
        return mav;
    }

    @GetMapping("/list")
    public ModelAndView getList(HttpSession session) {
        userId = (String) session.getAttribute("userId");
        List<Reserve> reserveList = reserveRepository.findById(userId);
        mav.addObject("reserveList", reserveList);
        mav.setViewName("list");
        return mav;
    }

    @GetMapping("/modify/{id}/{yadmNm}/{date}")
    public ModelAndView getModify(@PathVariable String id, @PathVariable String yadmNm, @PathVariable String date) {
        Reserve reserve = reserveRepository.findByIdAndYadmNmAndDate(id, yadmNm, date);
        mav.addObject("reserve", reserve);
        mav.setViewName("modify");
        return mav;
    }
}

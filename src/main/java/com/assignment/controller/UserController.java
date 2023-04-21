package com.assignment.controller;

import com.assignment.domain.User;
import com.assignment.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RequestMapping("/api/v1/users")
@Controller
@Slf4j
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/checkUserId")
    @ResponseBody
    public ResponseEntity checkUserId(String id){
        log.info(id);
        User user = userRepository.findById(id);
        if(user != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping("/join")
    @ResponseBody
    public ResponseEntity join(String name, String id, String password){
        log.info(id);
        User user = new User();
        user.setName(name);
        user.setId(id);
        user.setPassword(password);
        try {
            userRepository.insert(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }


    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity login(String id, String password, HttpSession session) {
        User user = userRepository.findByIdAndPassword(id, password);
        if (user != null){
            session.setAttribute("userId", user.getId());
            return new ResponseEntity<>(user.getCount(), HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

//
//    @GetMapping("/check")
//    public ResponseEntity checkJwt(@CookieValue(value = "token", required = false)String token){
//        Claims claims = jwtService.getClaims(token);
//
//        if(claims != null) {
//            String userName = claims.get("userName").toString();
//            return new ResponseEntity<>(userName, HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(null, HttpStatus.OK);
//    }

}

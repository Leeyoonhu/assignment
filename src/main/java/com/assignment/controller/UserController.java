package com.assignment.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.assignment.domain.Reserve;
import com.assignment.domain.User;
import com.assignment.repository.ReserveRepository;
import com.assignment.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.UUID;

@RequestMapping("/api/v1/users")
@Controller
@Slf4j
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/checkUserId")
    @ResponseBody
    public ResponseEntity postCheckUserId(String id) {
        log.info(id);
        User user = userRepository.findById(id);
        if (user != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping("/join")
    @ResponseBody
    public ResponseEntity postJoin(String name, String id, String password) {
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
    public ResponseEntity postLogin(String id, String password, HttpSession session) {
        User user;
        user = userRepository.findById(id);
        if (user != null) {
            user = userRepository.findByIdAndPassword(id, password);
            if (user != null) {
                session.setAttribute("userId", user.getId());
                return new ResponseEntity<>(user.getCount(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/logout")
    public String getLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }




}

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

    AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    ReserveRepository reserveRepository;

    @PostMapping("/checkUserId")
    @ResponseBody
    public ResponseEntity checkUserId(String id) {
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
    public ResponseEntity join(String name, String id, String password) {
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
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/reserve")
    @ResponseBody
    public ResponseEntity reserve(MultipartHttpServletRequest request, HttpSession session) throws Exception {
        Reserve reserve = new Reserve();
        MultipartFile uploadFile = request.getFile("uploadFile");
        log.info("uploadFile.getOriginalFilename() ::::::::::::: " + uploadFile.getOriginalFilename());
        log.info("uploadFile.getName() ::::::::::::::::: " + uploadFile.getName());

        reserve.setYadmNm(request.getParameter("yadmNm"));
        reserve.setHospUrl(request.getParameter("hospUrl"));
        reserve.setAddr(request.getParameter("addr"));
        reserve.setTelno(request.getParameter("telno"));
        reserve.setClCdNm(request.getParameter("clCdNm"));
        reserve.setId((String)session.getAttribute("userId"));
        reserve.setName(request.getParameter("name"));
        reserve.setPhoneNo(request.getParameter("phoneNo"));
        reserve.setSymptom(request.getParameter("symptom"));
        reserve.setDate(request.getParameter("date"));

        String imageFilePath = "image";
        String imageFileName = UUID.randomUUID() + uploadFile.getOriginalFilename();
        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(uploadFile.getContentType());
        objectMetaData.setContentLength(uploadFile.getSize());
        String bucketPath = imageFilePath + "/" + imageFileName;
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, bucketPath, uploadFile.getInputStream(), objectMetaData)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        uploadFile.getInputStream().close();
        String urlPath = amazonS3Client.getUrl(bucket, bucketPath).toString();
        reserve.setUploadFile(urlPath);

        try {
            reserveRepository.insert(reserve);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataIntegrityViolationException ex1) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

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

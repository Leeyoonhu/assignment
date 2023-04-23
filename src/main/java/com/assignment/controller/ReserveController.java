package com.assignment.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.assignment.domain.Reserve;
import com.assignment.repository.ReserveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@RequestMapping("/api/v1/reserve")
@Controller
@Slf4j
public class ReserveController {

    @Autowired
    AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    ReserveRepository reserveRepository;


    @PostMapping("/insert")
    @ResponseBody
    public ResponseEntity postReserve(MultipartHttpServletRequest request, HttpSession session) throws Exception {
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



}

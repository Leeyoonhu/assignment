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
    ReserveRepository reserveRepository;

    MultipartFile uploadFile;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    AmazonS3Client amazonS3Client;

    public String imgInsert(MultipartFile uploadFile) throws Exception {
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
        return amazonS3Client.getUrl(bucket, bucketPath).toString();
    }

    public void imgDelete(String originPath) {
        String key = "image/" + originPath.substring(originPath.lastIndexOf("/") + 1);
        amazonS3Client.deleteObject(bucket, key);
    }


    @PostMapping("/insert")
    @ResponseBody
    public ResponseEntity postInsert(MultipartHttpServletRequest request, HttpSession session) throws Exception {
        Reserve reserve = new Reserve();
        uploadFile = request.getFile("uploadFile");

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

        String urlPath = imgInsert(uploadFile);
        reserve.setUploadFile(urlPath);

        try {
            reserveRepository.insert(reserve);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataIntegrityViolationException ex1) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

    }

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity postSave(MultipartHttpServletRequest request, HttpSession session) throws Exception {
        Reserve reserve = reserveRepository.findByIdAndYadmNmAndDate((String)session.getAttribute("userId"), request.getParameter("yadmNm"), request.getParameter("lastDate"));

        if(request.getFile("uploadFile") != null){
            String originPath = reserve.getUploadFile();
            uploadFile = request.getFile("uploadFile");
            String urlPath = imgInsert(uploadFile);
            reserve.setUploadFile(urlPath);
            imgDelete(originPath);
        }

        reserve.setPhoneNo(request.getParameter("phoneNo"));
        reserve.setSymptom(request.getParameter("symptom"));
        reserve.setDate(request.getParameter("date"));

        // 중복검사 (동일날짜 가능, 다른날짜 예약있을때 불가능..)
        Reserve checkReserve = reserveRepository.findByIdAndYadmNmAndDate((String)session.getAttribute("userId"), request.getParameter("yadmNm"), reserve.getDate());
        if(checkReserve != null && !reserve.getDate().equals(request.getParameter("lastDate"))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            try {
                reserveRepository.save(reserve);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (DataIntegrityViolationException ex1) {
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
        }
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity postDelete(String yadmNm, String lastDate, HttpSession session){
        Reserve reserve = reserveRepository.findByIdAndYadmNmAndDate((String)session.getAttribute("userId"), yadmNm, lastDate);
        log.info(reserve.getUploadFile());
        imgDelete(reserve.getUploadFile());
        try {
            reserveRepository.delete(reserve);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataIntegrityViolationException ex1) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

}

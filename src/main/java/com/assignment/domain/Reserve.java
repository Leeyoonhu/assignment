package com.assignment.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "reserve", uniqueConstraints = {@UniqueConstraint(columnNames = {"yadm_nm", "date"})})
public class Reserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int count;

    @Column(name = "yadm_nm", length = 200, nullable = false)
    private String yadmNm;

    @Column(name = "hosp_url", length = 100, nullable = false)
    private String hospUrl;

    @Column(name = "addr", length = 300, nullable = false)
    private String addr;

    @Column(name = "telno", length = 100, nullable = false)
    private String telno;

    @Column(name = "cl_cd_nm", length = 100, nullable = false)
    private String clCdNm;

    @Column(name = "id", length = 100, nullable = false)
    private String id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "phone_no", length = 100, nullable = false)
    private String phoneNo;

    @Column(name = "symptom", length = 1000, nullable = false)
    private String symptom;

    @Column(name = "upload_file", length = 200, nullable = false)
    private String uploadFile;

    @Column(name = "date", length = 100, nullable = false)
    private String date;

}
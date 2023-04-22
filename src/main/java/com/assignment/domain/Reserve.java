package com.assignment.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "reserve", uniqueConstraints = {@UniqueConstraint(columnNames = {"yadmNm", "date"})})
public class Reserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int count;

    @Column(length = 200, nullable = false)
    private String yadmNm;

    @Column(length = 100, nullable = false)
    private String hospUrl;

    @Column(length = 300, nullable = false)
    private String addr;

    @Column(length = 100, nullable = false)
    private String telno;

    @Column(length = 100, nullable = false)
    private String clCdNm;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String phoneNo;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 1000, nullable = false)
    private String symptom;

    @Column(length = 200, nullable = false)
    private String uploadFile;

    @Column(length = 100, nullable = false)
    private String date;

}

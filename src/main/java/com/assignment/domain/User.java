package com.assignment.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String userName;

    @Column(length = 50, nullable = false, unique = true)
    private String userId;

    @Column(length = 100, nullable = false)
    private String password;
}

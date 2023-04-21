package com.assignment.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int count;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 50, nullable = false, unique = true)
    private String id;

    @Column(length = 100, nullable = false)
    private String password;
}

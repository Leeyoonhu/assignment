package com.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"com.assignment.repository"})
@SpringBootApplication
@ComponentScan(basePackages = {"com.assignment.controller", "com.assignment.domain", "com.assignment.repository", "com.assignment.service", "com.assignment.configuration"})
public class AssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentApplication.class, args);
	}

}

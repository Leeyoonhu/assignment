package com.assignment.repository;

import com.assignment.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM user WHERE id = :id", nativeQuery = true)
    User findById(String id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO user (name, id, password) VALUES (:#{#user.name}, :#{#user.id}, :#{#user.password})", nativeQuery = true)
    void insert(User user);

    @Query(value = "SELECT * FROM user WHERE id = :id AND password = :password", nativeQuery = true)
    User findByIdAndPassword(String id, String password);
}

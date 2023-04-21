package com.assignment.repository;

import com.assignment.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserId(String userId);

     @Transactional
     @Modifying
     @Query(value = "INSERT INTO user (user_name, user_id, password) VALUES (:#{#user.userName}, :#{#user.userId}, :#{#user.password})", nativeQuery = true)
     void insert(User user);
}

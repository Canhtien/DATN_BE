package com.alibou.security.repository;

import com.alibou.security.entity.Movie;
import com.alibou.security.entity.User;
import com.alibou.security.model.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
    Optional<User> findByUsernameOrEmail(String username, String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status = false WHERE u.id = :id")
    void deactivateUserById(Long id);
    @Query(value = "SELECT u FROM User u WHERE u.username LIKE %:username%",
            countQuery = "SELECT COUNT(u) FROM User u WHERE u.username LIKE %:username%")
    Page<User> findAllByUsernameContaining(String username, Pageable pageable);
}

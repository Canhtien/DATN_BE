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

import java.util.List;
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


    @Query("SELECT COUNT(u) FROM User u")
    long countAllUsers();

    @Query("SELECT COUNT(u) FROM User u WHERE DATE(u.createdAt) = CURRENT_DATE")
    long countNewUsersToday();

    @Query("SELECT COUNT(u) FROM User u WHERE MONTH(u.createdAt) = MONTH(CURRENT_DATE) AND YEAR(u.createdAt) = YEAR(CURRENT_DATE)")
    long countNewUsersThisMonth();

    @Query("SELECT u.role.name, COUNT(u) FROM User u GROUP BY u.role.name")
    List<Object[]> countUsersByRole();
}

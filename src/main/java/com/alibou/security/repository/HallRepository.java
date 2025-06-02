package com.alibou.security.repository;

import com.alibou.security.entity.Hall;
import com.alibou.security.entity.Movie;
import com.alibou.security.model.response.HallResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {
    Optional<Hall> findByName(String name);
    Optional<Hall> findById(Long id);
    @Query(value = "SELECT m FROM Movie m",
            countQuery = "SELECT COUNT(m) FROM Movie m")
    Page<HallResponse> findAllWithPagination(Pageable pageable);
}

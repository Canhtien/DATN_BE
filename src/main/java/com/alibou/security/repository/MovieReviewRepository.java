package com.alibou.security.repository;

import com.alibou.security.entity.MovieReview;
import com.alibou.security.model.response.MovieReviewResponse;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieReviewRepository extends JpaRepository<MovieReview, Long> {
    Optional<MovieReview> findByMovieId(Long movieId);
    @Query("SELECT COALESCE(SUM(r.rating), 0) FROM MovieReview r WHERE r.movie.id = :movieId")
    double sumRatingByMovieId(@Param("movieId") Long movieId);

    @Query("SELECT COUNT(r) FROM MovieReview r WHERE r.movie.id = :movieId")
    int countReviewsByMovieId(@Param("movieId") Long movieId);

    @Query("SELECT s FROM MovieReview s WHERE s.movie.id = :movieId")
    List<MovieReview> findMovieReviewByMovieId(@Param("movieId") Long movieId);


//    @Query(value = " SELECT s " +
//    " FROM MovieReview s " +
//   " LEFT JOIN s.movie m "+
//   " LEFT JOIN s.user u "+
//   " WHERE (:movieTitle IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :movieTitle, '%')))"+
//            " AND (:userName IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :userName, '%')))",
//            countQuery = "SELECT s FROM MovieReview s")
//    Page<MovieReview> findMovieReviewByMovieOrUser(@Param("movieTitle") String movieTitle, @Param("userName") String userName, Pageable pageable);


    @Query(value = """
    SELECT new com.alibou.security.model.response.MovieReviewResponse(
        u.username,
        m.title,
        s.content,
        s.rating,
        s.createdAt,
        s.updatedAt,
        s.createdBy,
        s.updatedBy
    )
    FROM MovieReview s
    LEFT JOIN s.movie m
    LEFT JOIN s.user u
    WHERE (:movieTitle IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :movieTitle, '%')))
      AND (:userName IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :userName, '%')))
""",
            countQuery = """
    SELECT COUNT(s)
    FROM MovieReview s
    LEFT JOIN s.movie m
    LEFT JOIN s.user u
""")
    Page<MovieReviewResponse> findMovieReviewByMovieOrUser(
            @Param("movieTitle") String movieTitle,
            @Param("userName") String userName,
            Pageable pageable);


    void deleteByMovieId(Long movieId);
}

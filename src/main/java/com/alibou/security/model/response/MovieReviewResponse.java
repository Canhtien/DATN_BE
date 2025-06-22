package com.alibou.security.model.response;

import com.alibou.security.entity.Movie;
import com.alibou.security.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieReviewResponse {

//    @ToString.Exclude
//    User user;
//    @ToString.Exclude
//    Movie movie;
    String username;
    String movieTitle;
    String content;
    Double rating;
    LocalDateTime createdAt = LocalDateTime.now();
    LocalDateTime updatedAt = LocalDateTime.now();
    Long createdBy;
    Long updatedBy;

    public MovieReviewResponse(String username, String movieTitle, String content, Double rating, LocalDateTime createdAt, LocalDateTime updatedAt, Long createdBy, Long updatedBy) {
        this.username = username;
        this.movieTitle = movieTitle;
        this.content = content;
        this.rating = rating;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }
}

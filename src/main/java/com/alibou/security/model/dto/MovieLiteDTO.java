package com.alibou.security.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovieLiteDTO {
    private Long id;
    private String title;
    private String posterUrl;
    private String genre;
    private String director;
    private String actor;
    private String runTime;
    private String summary;
    private String trailerUrl;
    private String thumbnailUrl;
    private String releaseDate;
}

package com.alibou.security.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class MovieDTO {
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
    private Double rating;
    private List<ShowTimeDTO> showtimes = new ArrayList<>();
    public MovieDTO(Long id, String title, String posterUrl, Double rating, String thumbnailUrl) {
        this.id = id;
        this.title = title;
        this.posterUrl = posterUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.rating = rating;
    }
    public void addShowtime(ShowTimeDTO showtime) {
        if (showtimes.stream().noneMatch(s -> s.getId().equals(showtime.getId()))) {
            showtimes.add(showtime);
        }
    }
    // getters, setters
}

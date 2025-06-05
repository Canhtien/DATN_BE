package com.alibou.security.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HallDTO {
    private Long id;
    private String name;
    private List<MovieDTO> movies = new ArrayList<>();
    private List<SeatRowDTO> seatRows = new ArrayList<>();
    public HallDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addMovie(MovieDTO movie) {
        if (movies.stream().noneMatch(m -> m.getId().equals(movie.getId()))) {
            movies.add(movie);
        }
    }

    // getters, setters
}

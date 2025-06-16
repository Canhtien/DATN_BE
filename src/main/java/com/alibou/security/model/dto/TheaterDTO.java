package com.alibou.security.model.dto;

// TheaterDTO.java
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TheaterDTO {
    private Long id;
    private String name;
    private List<HallDTO> halls = new ArrayList<>();
    private String code;
    private String location;
    private String phone;
    private String theaterImg;
    private List<ShowTimeDTO> showtimes = new ArrayList<>();
    public TheaterDTO(Long id, String name, String location, String code, String theaterImg) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.code = code;
        this.theaterImg = theaterImg;
    }

    public void addHall(Long hallId, String hallName, MovieDTO movie) {
        HallDTO hall = halls.stream()
                .filter(h -> h.getId().equals(hallId))
                .findFirst()
                .orElseGet(() -> {
                    HallDTO newHall = new HallDTO(hallId, hallName);
                    halls.add(newHall);
                    return newHall;
                });
        hall.addMovie(movie);
    }
    // getters, setters

}
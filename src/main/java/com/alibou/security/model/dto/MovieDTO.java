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
    private List<ShowTimeDTO> showtimes = new ArrayList<>();
    public MovieDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }
    public void addShowtime(ShowTimeDTO showtime) {
        if (showtimes.stream().noneMatch(s -> s.getId().equals(showtime.getId()))) {
            showtimes.add(showtime);
        }
    }
    // getters, setters
}

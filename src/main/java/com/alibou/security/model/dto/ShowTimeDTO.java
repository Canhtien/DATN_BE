package com.alibou.security.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class ShowTimeDTO {
    private Long id;
    private LocalDateTime startTime;
    private List<SeatDTO> seats = new ArrayList<>();
    private List<HallDTO> halls = new ArrayList<>();
    public ShowTimeDTO(Long id, LocalDateTime startTime) {
        this.id = id;
        this.startTime = startTime;
    }
}

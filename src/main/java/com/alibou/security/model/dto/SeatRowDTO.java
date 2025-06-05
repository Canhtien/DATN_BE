package com.alibou.security.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatRowDTO {
    private Long id;
    private String rowName;
    private Long hallId;
    private List<SeatDTO> seats = new ArrayList<>();
}

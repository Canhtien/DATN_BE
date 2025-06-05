package com.alibou.security.model.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatTypeDTO {
    private Long id;
    private String code;
    private String displayName;
    private String colorHex;
    private BigDecimal price;
}
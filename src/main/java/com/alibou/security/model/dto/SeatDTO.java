package com.alibou.security.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeatDTO {
    private Long id;
    private Integer seatNumber;
    private Long seatRowId;
    private Long seatType;
    private Long hallId;

    private String seatTypeCode;
    private String seatStatus;
    private BigDecimal seatPrice;

    public SeatDTO(Long seatId, Integer seatNumber, String seatStatus, Long seatType, String seatTypeCode) {
        this.id=seatId;
        this.seatNumber = seatNumber;
        this.seatStatus = seatStatus;
        this.seatType = seatType;
        this.seatTypeCode = seatTypeCode;
    }
}
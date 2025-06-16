package com.alibou.security.api.guest;


import com.alibou.security.model.dto.SeatDTO;
import com.alibou.security.model.dto.SeatRowDTO;
import com.alibou.security.model.dto.SeatTypeDTO;
import com.alibou.security.service.SeatRowService;
import com.alibou.security.service.SeatService;
import com.alibou.security.service.SeatTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/guest")
@RequiredArgsConstructor
public class GetSeatAPI {
    private final SeatTypeService seatTypeService;

    private final SeatService seatService;

    private final SeatRowService seatRowService;

    @GetMapping(value = "/seat-type")
    public List<SeatTypeDTO> getAllSeatType() {
        return seatTypeService.getAll();
    }

    @GetMapping("/seats/hall-id/{id}")
    public ResponseEntity<List<SeatDTO>> getSeatByHallId(@PathVariable Long id){
        List<SeatDTO> seats = seatService.getSeatsByHallId(id);
        return ResponseEntity.ok(seats);
    }

    @GetMapping("/seat_rows/hall-id/{id}")
    public ResponseEntity<List<SeatRowDTO>> getSeatRowByHallId(@PathVariable("id") Long hallId){
        List<SeatRowDTO> seatRows = seatRowService.getByHallId(hallId);
        return  ResponseEntity.ok(seatRows);
    }
}

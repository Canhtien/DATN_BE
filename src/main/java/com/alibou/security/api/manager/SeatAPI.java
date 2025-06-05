package com.alibou.security.api.manager;


import com.alibou.security.entity.Seat;
import com.alibou.security.model.dto.SeatDTO;
import com.alibou.security.model.dto.SeatRowDTO;
import com.alibou.security.model.dto.SeatTypeDTO;
import com.alibou.security.service.SeatRowService;
import com.alibou.security.service.SeatService;
import com.alibou.security.service.SeatTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/management")
@RequiredArgsConstructor
public class SeatAPI {

    private final SeatService seatService;

    private final SeatTypeService seatTypeService;

    private final SeatRowService seatRowService;

    @GetMapping("/seats")
    public List<Seat> getAllSeat() {
        return seatService.getAll();
    }

    @PostMapping("/seats")
    public Seat createSeat(@RequestBody SeatDTO dto) {
        return seatService.create(dto);
    }

    @PutMapping("/seats/{id}")
    public Seat updateSeat(@PathVariable Long id, @RequestBody SeatDTO dto) {
        return seatService.update(id, dto);
    }

    @DeleteMapping("/seats/{id}")
    public void deleteSeat(@PathVariable Long id) {
        seatService.delete(id);
    }
    @GetMapping("/seats/hall-id/{id}")
    public ResponseEntity<List<SeatDTO>> getSeatByHallId(@PathVariable Long id){
        List<SeatDTO> seats = seatService.getSeatsByHallId(id);
        return ResponseEntity.ok(seats);
    }


    @GetMapping("/seat-type")
    public List<SeatTypeDTO> getAllSeatType() {
        return seatTypeService.getAll();
    }

    @GetMapping("/seat-type/{id}")
    public SeatTypeDTO getSeatTypeById(@PathVariable Long id) {
        return seatTypeService.getById(id);
    }

    @PostMapping("/seat-type")
    public SeatTypeDTO createSeatType(@RequestBody SeatTypeDTO dto) {
        return seatTypeService.create(dto);
    }

    @PutMapping("/seat-type/{id}")
    public SeatTypeDTO updateSeatType(@PathVariable Long id, @RequestBody SeatTypeDTO dto) {
        return seatTypeService.update(id, dto);
    }

    @DeleteMapping("/seat-type/{id}")
    public void deleteSeatType(@PathVariable Long id) {
        seatTypeService.delete(id);
    }


    @GetMapping("/seat-row")
    public List<SeatRowDTO> getAllSeatRow() {
        return seatRowService.getAll();
    }

    @GetMapping("/seat-row/{id}")
    public SeatRowDTO getSeatRowById(@PathVariable Long id) {
        return seatRowService.getById(id);
    }

    @PostMapping("/seat-row")
    public SeatRowDTO createSeatRow(@RequestBody SeatRowDTO dto) {
        return seatRowService.create(dto);
    }

    @PutMapping("/seat-row/{id}")
    public SeatRowDTO updateSeatRow(@PathVariable Long id, @RequestBody SeatRowDTO dto) {
        return seatRowService.update(id, dto);
    }

    @DeleteMapping("/seat-row/{id}")
    public void deleteSeatRow(@PathVariable Long id) {
        seatRowService.delete(id);
    }
}


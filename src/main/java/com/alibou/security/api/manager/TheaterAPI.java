package com.alibou.security.api.manager;

import com.alibou.security.model.request.TheaterRequest;
import com.alibou.security.model.response.TheaterResponse;
import com.alibou.security.service.JPA.TheaterServiceJPA;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/management/theaters")
@RequiredArgsConstructor
public class TheaterAPI {

    private static final Logger logger = LoggerFactory.getLogger(TheaterAPI.class);
    private final TheaterServiceJPA service;



    @PostMapping
    public ResponseEntity<?> addTheater(@RequestBody TheaterRequest request) {
        try {
            TheaterResponse response = service.add(request);
            logger.info("Theater saved successfully: {}", request);
            return ResponseEntity.status(201).body(response);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to add theater: {}", e.getMessage());
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Failed to add theater: {}", e.getMessage());
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTheaterById(@PathVariable Long id){
        return ResponseEntity.status(200).body(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> changeTheater(@RequestBody TheaterRequest request, @PathVariable Long id) {
        try {
            TheaterResponse response = service.change(request, id);
            logger.info("Updated theater successfully: {}", request);
            return ResponseEntity.status(200).body(response);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to update hall with ID: {}", e.getMessage());
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Failed to update theater with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTheater(@PathVariable Long id) {
        try {
            service.delete(id);
            logger.info("Deleted theater successfully with ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.error("Failed to delete hall with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Failed to delete theater with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(500).body("Internal server error");
    }
}

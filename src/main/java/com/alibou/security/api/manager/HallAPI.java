package com.alibou.security.api.manager;

import com.alibou.security.model.request.HallRequest;
import com.alibou.security.model.response.HallResponse;
import com.alibou.security.service.JPA.HallServiceJPA;
import lombok.RequiredArgsConstructor;
//import org.hibernate.query.Page;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/management/halls")
@RequiredArgsConstructor
public class HallAPI {
    private static final Logger logger = LoggerFactory.getLogger(HallAPI.class);
    private final HallServiceJPA service;

    @GetMapping
    public ResponseEntity<List<HallResponse>> findAllHalls() {
        try {
            List<HallResponse> halls = service.findAll();
            logger.info("Retrieved all halls successfully");
            return ResponseEntity.ok(halls);
        } catch (Exception e) {
            logger.error("Failed to retrieve halls: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }
    @GetMapping("/pages")
    public ResponseEntity<Page<HallResponse>> findHallsByPages(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10")int pagesize){
    return ResponseEntity.ok(service.getAllHallsPaged(page, pagesize));
    }
    @PostMapping
    public ResponseEntity<?> addHall(@RequestBody HallRequest request) {
        try {
            service.add(request);
            logger.info("Hall added successfully: {}", request);
            return ResponseEntity.status(201).body(request);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to add hall: {}", e.getMessage());
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Failed to add hall: {}", e.getMessage());
            return ResponseEntity.status(500).body("Internal server error");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> changeHall(@RequestBody HallRequest request, @PathVariable Long id) {
        try {
            service.change(request, id);
            logger.info("Hall updated successfully: {}", request);
            return ResponseEntity.ok(request);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to update hall: {}", e.getMessage());
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Failed to update hall: {}", e.getMessage());
            return ResponseEntity.status(500).body("Internal server error");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHall(@PathVariable Long id) {
        try {
            service.delete(id);
            logger.info("Hall deleted successfully with ID: {}", id);
            return ResponseEntity.status(204).body(id);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to delete hall with ID: {}", e.getMessage());
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Failed to delete hall with ID: {}", e.getMessage());
            return ResponseEntity.status(500).body("Internal server error");
        }
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        logger.error("Unhandled exception occurred: {}", e.getMessage());
        return ResponseEntity.status(500).body("Internal server error");
    }
}

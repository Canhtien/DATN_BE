package com.alibou.security.service.JPA;

import com.alibou.security.config.GeneralMapper;
import com.alibou.security.entity.Hall;
import com.alibou.security.entity.Theater;
import com.alibou.security.model.request.HallRequest;
import com.alibou.security.model.response.HallResponse;
import com.alibou.security.repository.HallRepository;
import com.alibou.security.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HallServiceJPA {
    private static final Logger logger = LoggerFactory.getLogger(HallServiceJPA.class);
    private final HallRepository repository;
    private final UserServiceJPA userService;
    private final TheaterRepository theaterRepository;
    private final GeneralMapper generalMapper;

    public Hall getById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Hall not found"));
    }

    public HallResponse add(HallRequest request) {
        var existingHall = repository.findByName(request.getName());
        if (existingHall.isPresent()) {
            throw new IllegalArgumentException("Hall's name was exist");
        }
        var hall = generalMapper.mapToEntity(request, Hall.class);
        hall.setId(null);
        hall.setCreatedBy(userService.getCurrentUserId());
        hall.setCreatedAt(LocalDateTime.now());
        hall.setTheater(theaterRepository.findById(request.getTheaterId()).orElseThrow(() -> new IllegalArgumentException("Invalid theater ID")));
        repository.save(hall);
        logger.info("Hall added successfully: {}", hall);
        return generalMapper.mapToDTO(hall, HallResponse.class);
    }

    public HallResponse change(HallRequest request, Long id) {
        Hall existingHall = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Hall not found"));

        // Chỉ cập nhật các trường cho phép thay đổi
        existingHall.setName(request.getName());
        existingHall.setSeatCapacity(request.getSeatCapacity());
        existingHall.setStatus(request.getStatus());

        // Nếu cần update theater
        if (request.getTheaterId() != null && !request.getTheaterId().equals(existingHall.getTheater().getId())) {
            Theater newTheater = theaterRepository.findById(request.getTheaterId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid theater ID"));
            existingHall.setTheater(newTheater);
        }

        existingHall.setUpdatedAt(LocalDateTime.now());
        existingHall.setUpdatedBy(userService.getCurrentUserId());

        repository.save(existingHall);

        return generalMapper.mapToDTO(existingHall, HallResponse.class);
    }

    public void delete(Long id) {
        var existingHall = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Hall not found"));
        repository.deleteById(existingHall.getId());
        logger.info("Hall deleted successfully: {}", id);
    }

    public List<HallResponse> findAll() {
        List<Hall> halls = repository.findAll();
        logger.info("Halls retrieved successfully");
        return halls.stream()
                .map(hall -> generalMapper.mapToDTO(hall, HallResponse.class))
                .toList();
    }
    public Page<HallResponse> getAllHallsPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAllWithPagination(pageable);
    }
}

package com.alibou.security.service.Impl;

import com.alibou.security.model.dto.SeatTypeDTO;
import com.alibou.security.entity.SeatType;
import com.alibou.security.repository.SeatTypeRepository;
import com.alibou.security.service.SeatTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatTypeServiceImpl implements SeatTypeService {

    private final SeatTypeRepository seatTypeRepository;

    @Override
    public List<SeatTypeDTO> getAll() {
        return seatTypeRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SeatTypeDTO getById(Long id) {
        SeatType entity = seatTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SeatType not found"));
        return toDto(entity);
    }

    @Override
    public SeatTypeDTO create(SeatTypeDTO dto) {
        if (seatTypeRepository.existsByCode(dto.getCode())) {
            throw new RuntimeException("SeatType code already exists");
        }

        SeatType seatType = toEntity(dto);
        return toDto(seatTypeRepository.save(seatType));
    }

    @Override
    public SeatTypeDTO update(Long id, SeatTypeDTO dto) {
        SeatType seatType = seatTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SeatType not found"));

        seatType.setCode(dto.getCode());
        seatType.setDisplayName(dto.getDisplayName());
        seatType.setColorHex(dto.getColorHex());
        seatType.setPrice(dto.getPrice());

        return toDto(seatTypeRepository.save(seatType));
    }

    @Override
    public void delete(Long id) {
        seatTypeRepository.deleteById(id);
    }

    private SeatTypeDTO toDto(SeatType entity) {
        return SeatTypeDTO.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .displayName(entity.getDisplayName())
                .colorHex(entity.getColorHex())
                .price(entity.getPrice())
                .build();
    }

    private SeatType toEntity(SeatTypeDTO dto) {
        return SeatType.builder()
                .code(dto.getCode())
                .displayName(dto.getDisplayName())
                .colorHex(dto.getColorHex())
                .price(dto.getPrice())
                .build();
    }
}

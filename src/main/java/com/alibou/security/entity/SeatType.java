package com.alibou.security.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
@Entity
@Table(name = "seat_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    private String code; // e.g. NORMAL, VIP

    @Column(name = "display_name")
    private String displayName; // "Ghế thường", "Ghế VIP"

    @Column(name = "color_hex")
    private String colorHex; // Màu hiển thị trong UI (optional)

    @Column(name = "price", nullable = false)
    private BigDecimal price; // <-- ✅ Giá của loại ghế này

    @OneToMany(mappedBy = "seatType")
    private Set<Seat> seats;
}

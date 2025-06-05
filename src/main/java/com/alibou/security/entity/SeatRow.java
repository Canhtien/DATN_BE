package com.alibou.security.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "seat_rows")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"hall", "seats"})
@EqualsAndHashCode(exclude = {"hall", "seats"})
public class SeatRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "row_name", nullable = false)
    private String rowName; // A, B, C...

    @ManyToOne
    @JoinColumn(name = "hall_id")
    @JsonBackReference
    private Hall hall;

    @OneToMany(mappedBy = "seatRow", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Seat> seats = new HashSet<>();
}


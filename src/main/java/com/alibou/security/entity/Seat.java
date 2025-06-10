package com.alibou.security.entity;

import com.alibou.security.enums.SeatStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seats")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"seatRow", "ticket"})
@EqualsAndHashCode(exclude = {"seatRow", "ticket"})
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber; // 1, 2, 3...

    @ManyToOne
    @JoinColumn(name = "seat_type_id", nullable = false)
    @JsonBackReference
    private SeatType seatType;


    @Enumerated(EnumType.STRING)
    private SeatStatus status = SeatStatus.AVAILABLE;

    @ManyToOne
    @JoinColumn(name = "seat_row_id")
    @JsonBackReference
    private SeatRow seatRow;

    @OneToOne(mappedBy = "seat")
    @JsonBackReference
    private Ticket ticket;



//    @ManyToOne
//    @JoinColumn(name = "seat_price_id", nullable = false)
//    @JsonBackReference
//    private SeatType seatPrice;
}

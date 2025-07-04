package com.alibou.security.entity;

import com.alibou.security.enums.TicketStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"seat_id", "showtime_id"})
})
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "showtime_id")
    @JsonBackReference
    private Showtime showtime;

    @ManyToOne
    @JoinColumn(name = "discount_application_id")
    @JsonBackReference
    private DiscountApplication discountApplication;

    @OneToMany(mappedBy = "ticket")
    @JsonBackReference
    private Set<PaymentTicket> paymentTickets = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "seat_id")
    @JsonBackReference
    private Seat seat;

    @Column(name = "ticket_type", nullable = false)
    private String ticketType;

    private BigDecimal price;

    @Column(name = "service_fee", nullable = false)
    private BigDecimal serviceFee = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", updatable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "created_by", updatable = false)
    private Long createdBy;

    @Column(name = "updated_by", updatable = false)
    private Long updatedBy;
}

package com.alibou.security.service.JPA;

import com.alibou.security.entity.*;
import com.alibou.security.enums.TicketStatus;
import com.alibou.security.mapper.TicketMapper;
import com.alibou.security.model.request.TicketRequest;
import com.alibou.security.model.response.TicketResponse;
import com.alibou.security.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TicketServiceJPA {
    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    TicketMapper ticketMapper;
    @Autowired
    private ShowTimeRepository showTimeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DiscountRepository discountRepository;

    @Autowired
    DiscountApplicationRepository discountApplicationRepository;

    @Autowired
    SeatRepository seatRepository;

    private static final Logger logger = LoggerFactory.getLogger(TicketServiceJPA.class);
    @Autowired
    private UserServiceJPA userService;

    public TicketResponse createTicket(TicketRequest ticketRequest) {
        if(ticketRepository.existsByShowtimeIdAndSeatId(ticketRequest.getShowtime_id(), ticketRequest.getSeatId())) {
            throw new ApplicationContextException("Ticket already exists");
        }

        Ticket ticket = ticketMapper.toTicket(ticketRequest);
        ticket.setId(null);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ticket.setCreatedAt(timestamp.toLocalDateTime());
        ticket.setUpdatedAt(timestamp.toLocalDateTime());
        ticket.setCreatedBy(userService.getCurrentUserId());

        Seat seat = seatRepository.findById(ticketRequest.getSeatId()).orElseThrow(() -> new ApplicationContextException("Seat not found"));
        ticket.setSeat(seat);

        User user = userRepository.findById(Math.toIntExact(ticketRequest.getUser_id())).orElseThrow(() -> new ApplicationContextException("User not found"));
        ticket.setUser(user);

        Showtime showtime = showTimeRepository.findById(ticketRequest.getShowtime_id()).orElseThrow(() -> new ApplicationContextException("Showtime not found"));
        ticket.setShowtime(showtime);

        DiscountApplication discountApplication = discountApplicationRepository.findById(ticketRequest.getDiscount_application_id())
                .orElse(null);
        ticket.setDiscountApplication(discountApplication);
        try {
           ticket = ticketRepository.save(ticket);
           return ticketMapper.toTicketResponse(ticket);

        } catch (Exception e) {
            log.error("Error creating ticket", e);
            throw new ApplicationContextException("Error creating ticket", e);
        }
    }

    public TicketResponse updateTicket(long id,TicketRequest ticketRequest) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new ApplicationContextException("Ticket does not exist"));
         ticketMapper.updateTicket(ticket,ticketRequest);

         Timestamp timestamp = new Timestamp(System.currentTimeMillis());
         ticket.setUpdatedAt(timestamp.toLocalDateTime());
         ticket.setUpdatedBy(userService.getCurrentUserId());
         return ticketMapper.toTicketResponse(ticketRepository.save(ticket));
    }

    public void deleteTicket(long id) {
        if (!ticketRepository.existsById(id)) {
            throw new ApplicationContextException("Ticket does not exist");
        }
        ticketRepository.deleteById(id);
    }


    public Page<TicketResponse> getAllTicket(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ticketRepository.findAllTickets(id, pageable);
    }

    public TicketResponse getTicket(long id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new ApplicationContextException("Ticket does not exist"));

        return ticketMapper.toTicketResponse(ticket);
    }

    @Transactional
    @Scheduled(cron = "0 */20 * * * ?")
    public ResponseEntity<List<Long>> deleteExpiredPendingTickets() {
        // Lấy danh sách tất cả các vé có status PENDING
        List<Ticket> pendingTickets = ticketRepository.findByStatus(TicketStatus.PENDING);
        List<Long> deletedTicketIds = new ArrayList<>();

        if (pendingTickets.isEmpty()) {
            logger.info("No pending tickets found");
            return ResponseEntity.ok(Collections.emptyList());
        }

        LocalDateTime now = LocalDateTime.now();

        for (Ticket ticket : pendingTickets) {
            if (ticket == null) {
                logger.warn("Ticket is null, skipping...");
                continue;
            }

            // Nếu muốn kiểm tra theo createdAt:
            LocalDateTime createdAt = ticket.getCreatedAt();

            // Kiểm tra nếu đã quá 20 phút kể từ khi tạo
            if (createdAt != null && createdAt.plusMinutes(20).isBefore(now)) {
                ticketRepository.deleteById(ticket.getId());
                deletedTicketIds.add(ticket.getId());
                logger.info("Deleted expired pending ticket with ID: " + ticket.getId());
            }
        }

        if (deletedTicketIds.isEmpty()) {
            logger.info("No expired pending tickets to delete");
        }

        return ResponseEntity.ok(deletedTicketIds);
    }


    public TicketResponse CheckAndUpdateTicketStatus(long ticketId, TicketStatus status, long userId) {

       Ticket  ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new ApplicationContextException("Ticket does not exist"));

        if (status == TicketStatus.CANCELED || status == TicketStatus.REFUNDED) {
            if(ticket.getStatus() == TicketStatus.USED || ticket.getStatus() == TicketStatus.EXPIRED){

                    throw new IllegalStateException("Cannot cancel or refund ticket that is used or expired");
            }

            ticket.setStatus(status);
        } else if (status == TicketStatus.USED) {

            if (ticket.getStatus() != TicketStatus.PAID) {
                throw new IllegalStateException("Only pending ticket can be marked as used.");
            }
            ticket.setStatus(TicketStatus.USED);

        } else if (status == TicketStatus.PAID) {
            if (ticket.getStatus() != TicketStatus.PENDING) {
                throw new IllegalStateException("Only pending ticket can be marked as paid.");
            }
            ticket.setStatus(TicketStatus.PAID);
        }

        ticket.setUpdatedAt(LocalDateTime.now());
        ticket.setUpdatedBy(userId);
        ticketRepository.save(ticket);

        return ticketMapper.toTicketResponse(ticket);

    }

    public List<TicketResponse> getTicketByUserId(long userId) {
        return ticketRepository.findAllByUserId(userId);
    }
        public List<TicketResponse> getTicketByShowtimeId(Long showtimeId) {
            List<Object[]> results = ticketRepository.findAllByShowtimeId(showtimeId);
            return results.stream()
                    .map(obj -> {
                        TicketResponse response = new TicketResponse();
                        response.setStatus((TicketStatus) obj[0]);
                        response.setSeatId((Long) obj[1]);
                        return response;
                    })
                    .collect(Collectors.toList());
        }
 }

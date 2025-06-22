package com.alibou.security.api.manager;

import com.alibou.security.api.user.TicketAPI;
import com.alibou.security.mapper.TicketMapper;
import com.alibou.security.model.request.TicketRequest;
import com.alibou.security.model.response.TicketResponse;
import com.alibou.security.repository.TicketRepository;
import com.alibou.security.service.JPA.TicketServiceJPA;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/management/ticket")
@RequiredArgsConstructor
@Slf4j
public class TicketForManagementAPI {


        private final TicketServiceJPA ticketService;
        private final Logger logger = LoggerFactory.getLogger(TicketAPI.class);

        private final TicketRepository ticketRepository;
        private final TicketMapper ticketMapper;

        @GetMapping
        public ResponseEntity<Page<TicketResponse>> getAllTickets(
                @RequestParam(required = false)Long id,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10")int size
        ) {
            try {
                Page<TicketResponse> tickets = ticketService.getAllTicket(id, page, size);
                return ResponseEntity.ok(tickets);
            } catch (Throwable e) {
                logger.error("Error getting all tickets.", e.getMessage());
                return ResponseEntity.status(500).body(null);
            }
        }
        @PutMapping("/{id}")
        public ResponseEntity<?> updateTicket(@PathVariable long id, @RequestBody TicketRequest ticketRequest) {
            try {
                if (ticketRequest == null) {
                    throw new IllegalArgumentException("Missing required fields");
                }

                TicketResponse ticketResponse = ticketService.updateTicket(id, ticketRequest);
                logger.info("Ticket updated: {}", ticketResponse);
                return ResponseEntity.ok(ticketResponse);
            } catch (Throwable e) {
                logger.error("Error saving ticket with id: {}", e.getMessage(), id);
                return ResponseEntity.status(500).body(e.getMessage());
            }
        }
}

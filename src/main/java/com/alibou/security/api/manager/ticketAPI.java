package com.alibou.security.api.manager;

import com.alibou.security.api.user.TicketAPI;
import com.alibou.security.mapper.TicketMapper;
import com.alibou.security.model.response.TicketResponse;
import com.alibou.security.repository.TicketRepository;
import com.alibou.security.service.JPA.TicketServiceJPA;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/management/ticket")
@RequiredArgsConstructor
@Slf4j
public class ticketAPI {

    private final TicketServiceJPA ticketService;
    private final Logger logger = LoggerFactory.getLogger(TicketAPI.class);

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @GetMapping
    public ResponseEntity<List<TicketResponse>> getAllTickets() {
        try {
            List<TicketResponse> tickets = ticketService.getAllTicket();
            return ResponseEntity.ok(tickets);
        } catch (Throwable e) {
            logger.error("Error getting all tickets.", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }
}

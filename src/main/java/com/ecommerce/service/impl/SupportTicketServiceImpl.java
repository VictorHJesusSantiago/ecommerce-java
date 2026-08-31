package com.ecommerce.service.impl;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.entity.*;
import com.ecommerce.repository.*;
import com.ecommerce.service.SupportTicketService;
import com.ecommerce.util.CodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupportTicketServiceImpl implements SupportTicketService {

    private final SupportTicketRepository supportTicketRepository;
    private final TicketMessageRepository ticketMessageRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public SupportTicket createTicket(Long userId, String subject, String message, String priority, String category) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        SupportTicket ticket = SupportTicket.builder()
                .ticketNumber(CodeUtil.generateTicketNumber())
                .user(user)
                .subject(subject)
                .status("OPEN")
                .priority(priority != null ? priority : "MEDIUM")
                .category(category)
                .build();
        SupportTicket saved = supportTicketRepository.save(ticket);

        TicketMessage ticketMessage = TicketMessage.builder()
                .ticket(saved)
                .user(user)
                .message(message)
                .isStaff(false)
                .build();
        ticketMessageRepository.save(ticketMessage);

        log.info("Support ticket created: {} by user {}", saved.getTicketNumber(), userId);
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public SupportTicket getTicketById(Long id) {
        return supportTicketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SupportTicket", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupportTicket> getTicketsByUserId(Long userId) {
        return supportTicketRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupportTicket> getAllTickets() {
        return supportTicketRepository.findAll();
    }

    @Override
    @Transactional
    public void addMessage(Long ticketId, Long userId, String message) {
        SupportTicket ticket = getTicketById(ticketId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        TicketMessage ticketMessage = TicketMessage.builder()
                .ticket(ticket)
                .user(user)
                .message(message)
                .isStaff(false)
                .build();
        ticketMessageRepository.save(ticketMessage);

        ticket.setLastReplyAt(LocalDateTime.now());
        ticket.setRepliesCount(ticket.getRepliesCount() + 1);
        supportTicketRepository.save(ticket);
    }

    @Override
    @Transactional
    public void updateStatus(Long ticketId, String status) {
        SupportTicket ticket = getTicketById(ticketId);
        ticket.setStatus(status);
        supportTicketRepository.save(ticket);
    }

    @Override
    @Transactional
    public void updatePriority(Long ticketId, String priority) {
        SupportTicket ticket = getTicketById(ticketId);
        ticket.setPriority(priority);
        supportTicketRepository.save(ticket);
    }

    @Override
    @Transactional
    public void assignTicket(Long ticketId, Long assignedTo) {
        SupportTicket ticket = getTicketById(ticketId);
        ticket.setAssignedToId(assignedTo);
        ticket.setStatus("IN_PROGRESS");
        supportTicketRepository.save(ticket);
    }

    @Override
    @Transactional
    public void closeTicket(Long ticketId) {
        SupportTicket ticket = getTicketById(ticketId);
        ticket.setStatus("CLOSED");
        ticket.setClosedAt(LocalDateTime.now());
        supportTicketRepository.save(ticket);
    }
}

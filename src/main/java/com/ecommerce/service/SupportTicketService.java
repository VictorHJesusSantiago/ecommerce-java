package com.ecommerce.service;

import com.ecommerce.model.entity.SupportTicket;
import com.ecommerce.model.entity.TicketMessage;

import java.util.List;

public interface SupportTicketService {
    SupportTicket createTicket(Long userId, String subject, String message, String priority, String category);
    SupportTicket getTicketById(Long id);
    List<SupportTicket> getTicketsByUserId(Long userId);
    List<SupportTicket> getAllTickets();
    void addMessage(Long ticketId, Long userId, String message);
    void updateStatus(Long ticketId, String status);
    void updatePriority(Long ticketId, String priority);
    void assignTicket(Long ticketId, Long assignedTo);
    void closeTicket(Long ticketId);
}

package com.ecommerce.repository;

import com.ecommerce.model.entity.SupportTicket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {

    Optional<SupportTicket> findByTicketNumber(String ticketNumber);

    Page<SupportTicket> findByUserId(Long userId, Pageable pageable);

    Page<SupportTicket> findByAssignedToId(Long assignedToId, Pageable pageable);

    Page<SupportTicket> findByStatus(String status, Pageable pageable);

    @Query("SELECT t FROM SupportTicket t WHERE t.status NOT IN ('RESOLVED', 'CLOSED') ORDER BY CASE t.priority WHEN 'CRITICAL' THEN 1 WHEN 'URGENT' THEN 2 WHEN 'HIGH' THEN 3 WHEN 'MEDIUM' THEN 4 ELSE 5 END")
    List<SupportTicket> findOpenTicketsByPriority();

    @Query("SELECT t FROM SupportTicket t WHERE t.assignedTo IS NULL AND t.status = 'OPEN'")
    List<SupportTicket> findUnassignedTickets();

    long countByStatus(String status);

    long countByAssignedToIdAndStatusNotIn(Long assignedToId, List<String> statuses);
}

package com.github.rlimapro.eventticketplatform.service;

import com.github.rlimapro.eventticketplatform.domain.entities.Ticket;

import java.util.UUID;

public interface TicketTypeService {
    Ticket purchaseTicket(UUID userId, UUID ticketTypeId);
}

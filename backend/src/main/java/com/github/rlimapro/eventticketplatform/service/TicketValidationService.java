package com.github.rlimapro.eventticketplatform.service;

import com.github.rlimapro.eventticketplatform.domain.entities.TicketValidation;

import java.util.UUID;

public interface TicketValidationService {
    TicketValidation validateTicketByQrCode(UUID qrCodeId);
    TicketValidation validateTicketManually(UUID ticketId);
}

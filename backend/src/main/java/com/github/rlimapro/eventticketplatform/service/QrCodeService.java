package com.github.rlimapro.eventticketplatform.service;

import com.github.rlimapro.eventticketplatform.domain.entities.QrCode;
import com.github.rlimapro.eventticketplatform.domain.entities.Ticket;

import java.util.UUID;

public interface QrCodeService {
    QrCode generateQrCode(Ticket ticket);
    byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId);
}

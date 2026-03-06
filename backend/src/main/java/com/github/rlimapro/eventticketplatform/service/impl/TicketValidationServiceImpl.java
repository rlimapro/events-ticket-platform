package com.github.rlimapro.eventticketplatform.service.impl;

import com.github.rlimapro.eventticketplatform.domain.entities.QrCode;
import com.github.rlimapro.eventticketplatform.domain.entities.Ticket;
import com.github.rlimapro.eventticketplatform.domain.entities.TicketValidation;
import com.github.rlimapro.eventticketplatform.domain.enums.TicketValidationMethodEnum;
import com.github.rlimapro.eventticketplatform.domain.enums.TicketValidationStatusEnum;
import com.github.rlimapro.eventticketplatform.exception.QrCodeNotFoundException;
import com.github.rlimapro.eventticketplatform.exception.TicketNotFoundException;
import com.github.rlimapro.eventticketplatform.repository.QrCodeRepository;
import com.github.rlimapro.eventticketplatform.repository.TicketRepository;
import com.github.rlimapro.eventticketplatform.repository.TicketValidationRepository;
import com.github.rlimapro.eventticketplatform.service.TicketValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketValidationServiceImpl implements TicketValidationService {

    private final QrCodeRepository qrCodeRepository;
    private final TicketValidationRepository ticketValidationRepository;
    private final TicketRepository ticketRepository;

    @Override
    public TicketValidation validateTicketByQrCode(UUID qrCodeId) {
        QrCode qrCode = qrCodeRepository.findById(qrCodeId).orElseThrow(
            () -> new QrCodeNotFoundException("QR code with ID " + qrCodeId + " not found")
        );

        Ticket ticket = qrCode.getTicket();
        return validateTicket(ticket, TicketValidationMethodEnum.QR_SCAN);
    }

    @Override
    public TicketValidation validateTicketManually(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
            TicketNotFoundException::new
        );

        return validateTicket(ticket, TicketValidationMethodEnum.MANUAL);
    }

    private TicketValidation validateTicket(Ticket ticket, TicketValidationMethodEnum validationMethod) {
        TicketValidation ticketValidation = new TicketValidation();
        ticketValidation.setTicket(ticket);
        ticketValidation.setValidationMethod(validationMethod);

        TicketValidationStatusEnum ticketValidationStatus = ticket.getValidations().stream()
            .filter(v -> v.getStatus().equals(TicketValidationStatusEnum.VALID))
            .findFirst()
            .map(v -> TicketValidationStatusEnum.INVALID)
            .orElse(TicketValidationStatusEnum.VALID);

        ticketValidation.setStatus(ticketValidationStatus);

        return ticketValidationRepository.save(ticketValidation);
    }
}

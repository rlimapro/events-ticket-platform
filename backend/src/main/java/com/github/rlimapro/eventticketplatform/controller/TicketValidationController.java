package com.github.rlimapro.eventticketplatform.controller;

import com.github.rlimapro.eventticketplatform.domain.entities.TicketValidation;
import com.github.rlimapro.eventticketplatform.domain.enums.TicketValidationMethodEnum;
import com.github.rlimapro.eventticketplatform.dto.TicketValidationRequestDto;
import com.github.rlimapro.eventticketplatform.dto.TicketValidationResponseDto;
import com.github.rlimapro.eventticketplatform.mapper.TicketValidationMapper;
import com.github.rlimapro.eventticketplatform.service.TicketValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ticket-validations")
@RequiredArgsConstructor
public class TicketValidationController {

    private final TicketValidationMapper ticketValidationMapper;
    private final TicketValidationService ticketValidationService;

    @PostMapping
    public ResponseEntity<TicketValidationResponseDto> validateTicket(
        @RequestBody TicketValidationRequestDto ticketValidationRequestDto
    ) {
        TicketValidationMethodEnum method = ticketValidationRequestDto.getMethod();
        TicketValidation ticketValidation;

        UUID id = ticketValidationRequestDto.getId();

        if(method.equals(TicketValidationMethodEnum.QR_SCAN)) {
            ticketValidation = ticketValidationService.validateTicketByQrCode(id);
        }

        ticketValidation = ticketValidationService.validateTicketManually(id);

        return ResponseEntity.ok(ticketValidationMapper.toDto(ticketValidation));
    }
}

package com.github.rlimapro.eventticketplatform.dto;

import com.github.rlimapro.eventticketplatform.domain.enums.TicketValidationMethodEnum;
import com.github.rlimapro.eventticketplatform.domain.enums.TicketValidationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidationResponseDto {
    private UUID ticketId;
    private TicketValidationStatusEnum status;
}

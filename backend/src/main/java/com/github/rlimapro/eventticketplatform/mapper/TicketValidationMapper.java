package com.github.rlimapro.eventticketplatform.mapper;

import com.github.rlimapro.eventticketplatform.domain.entities.TicketValidation;
import com.github.rlimapro.eventticketplatform.dto.TicketValidationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketValidationMapper {

    @Mapping(target = "ticketId", source = "ticket.id")
    TicketValidationResponseDto toDto(TicketValidation ticketValidation);
}

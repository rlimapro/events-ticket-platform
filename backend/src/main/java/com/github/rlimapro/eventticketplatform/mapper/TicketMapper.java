package com.github.rlimapro.eventticketplatform.mapper;

import com.github.rlimapro.eventticketplatform.domain.entities.Ticket;
import com.github.rlimapro.eventticketplatform.domain.entities.TicketType;
import com.github.rlimapro.eventticketplatform.dto.GetTicketResponseDto;
import com.github.rlimapro.eventticketplatform.dto.ListTicketResponseDto;
import com.github.rlimapro.eventticketplatform.dto.ListTicketTicketTypeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {
    ListTicketTicketTypeResponseDto toDto(TicketType ticketType);
    ListTicketResponseDto toDto(Ticket ticket);

    @Mapping(target = "price", source = "ticket.ticketType.price")
    @Mapping(target = "description", source = "ticket.ticketType.description")
    @Mapping(target = "eventName", source = "ticket.ticketType.event.name")
    @Mapping(target = "eventVenue", source = "ticket.ticketType.event.venue")
    @Mapping(target = "eventStart", source = "ticket.ticketType.event.start")
    @Mapping(target = "eventEnd", source = "ticket.ticketType.event.end")
    GetTicketResponseDto toGetTicketResponseDto(Ticket ticket);
}

package com.github.rlimapro.eventticketplatform.mapper;

import com.github.rlimapro.eventticketplatform.domain.CreateEventRequest;
import com.github.rlimapro.eventticketplatform.domain.CreateTicketTypeRequest;
import com.github.rlimapro.eventticketplatform.domain.UpdateEventRequest;
import com.github.rlimapro.eventticketplatform.domain.UpdateTicketTypeRequest;
import com.github.rlimapro.eventticketplatform.domain.entities.Event;
import com.github.rlimapro.eventticketplatform.domain.entities.TicketType;
import com.github.rlimapro.eventticketplatform.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);
    CreateEventRequest fromDto(CreateEventRequestDto dto);
    CreateEventResponseDto toDto(Event dto);

    ListEventResponseDto toListEventResponseDto(Event event);
    ListEventTicketTypeResponseDto toListEventTicketTypeResponseDto(TicketType ticketType);

    GetEventDetailsResponseDto toGetEventDetailsResponseDto(Event event);
    GetEventDetailsTicketTypeResponseDto toGetEventDetailsTicketTypeResponseDto(TicketType ticketType);

    UpdateTicketTypeRequest fromDto(UpdateTicketTypeRequestDto dto);
    UpdateEventRequest fromDto(UpdateEventRequestDto dto);
    UpdateTicketTypeResponseDto toUpdateTicketTypeResponseDto(TicketType ticketType);
    UpdateEventResponseDto toUpdateEventResponseDto(Event event);

    ListPublishedEventResponseDto toListPublishedEventResponseDto(Event event);

    GetPublishedEventDetailsTicketTypeResponseDto toGetPublishedEventDetailsTicketTypeResponseDto(TicketType ticketType);
    GetPublishedEventDetailsResponseDto toGetPublishedEventDetailsResponseDto(Event event);


}

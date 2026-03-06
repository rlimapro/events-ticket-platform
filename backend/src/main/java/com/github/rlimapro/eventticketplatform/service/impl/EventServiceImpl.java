package com.github.rlimapro.eventticketplatform.service.impl;

import com.github.rlimapro.eventticketplatform.domain.CreateEventRequest;
import com.github.rlimapro.eventticketplatform.domain.UpdateEventRequest;
import com.github.rlimapro.eventticketplatform.domain.UpdateTicketTypeRequest;
import com.github.rlimapro.eventticketplatform.domain.entities.Event;
import com.github.rlimapro.eventticketplatform.domain.entities.TicketType;
import com.github.rlimapro.eventticketplatform.domain.entities.User;
import com.github.rlimapro.eventticketplatform.domain.enums.EventStatusEnum;
import com.github.rlimapro.eventticketplatform.exception.EventNotFoundException;
import com.github.rlimapro.eventticketplatform.exception.EventUpdateException;
import com.github.rlimapro.eventticketplatform.exception.TicketTypeNotFoundException;
import com.github.rlimapro.eventticketplatform.exception.UserNotFoundException;
import com.github.rlimapro.eventticketplatform.repository.EventRepository;
import com.github.rlimapro.eventticketplatform.repository.UserRepository;
import com.github.rlimapro.eventticketplatform.service.EventService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public Event createEvent(UUID organizerId, CreateEventRequest event) {

        User organizer = userRepository.findById(organizerId)
            .orElseThrow(() -> new UserNotFoundException("User with ID " + organizerId + " not found"));

        Event eventToCreate = new Event();

        List<TicketType> ticketTypesToCreate = event.getTicketTypes().stream().map(
        ticketType -> {
            TicketType ticketTypeToCreate = new TicketType();
            ticketTypeToCreate.setName(ticketType.getName());
            ticketTypeToCreate.setPrice(ticketType.getPrice());
            ticketTypeToCreate.setDescription(ticketType.getDescription());
            ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
            ticketTypeToCreate.setEvent(eventToCreate);
            return ticketTypeToCreate;
        }).toList();

        eventToCreate.setName(event.getName());
        eventToCreate.setStart(event.getStart());
        eventToCreate.setEnd(event.getEnd());
        eventToCreate.setVenue(event.getVenue());
        eventToCreate.setSalesStart(event.getSalesStart());
        eventToCreate.setSalesEnd(event.getSalesEnd());
        eventToCreate.setStatus(event.getStatus());
        eventToCreate.setOrganizer(organizer);
        eventToCreate.setTicketTypes(ticketTypesToCreate);

        return eventRepository.save(eventToCreate);
    }

    @Override
    public Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable) {
        return eventRepository.findByOrganizerId(organizerId, pageable);
    }

    @Override
    public Optional<Event> getEventForOrganizer(UUID organizerId, UUID id) {
        return eventRepository.findByIdAndOrganizerId(id, organizerId);
    }

    @Override
    @Transactional
    public Event updateEventForOrganizer(UUID organizedId, UUID id, UpdateEventRequest request) {
        if(request.getId() == null) {
            throw new EventUpdateException("Event with ID " + id + " not found");
        }

        if(!id.equals(request.getId())) {
            throw new EventUpdateException("Cannot update the ID of an event");
        }

        Event existingEvent = eventRepository.findByIdAndOrganizerId(id, organizedId)
            .orElseThrow(() -> new EventNotFoundException("Event with ID " + id + " not found"));

        existingEvent.setName(request.getName());
        existingEvent.setStart(request.getStart());
        existingEvent.setEnd(request.getEnd());
        existingEvent.setVenue(request.getVenue());
        existingEvent.setSalesStart(request.getSalesStart());
        existingEvent.setSalesEnd(request.getSalesEnd());
        existingEvent.setStatus(request.getStatus());

        Set<UUID> requestTicketTypeIds = request.getTicketTypes()
            .stream()
            .map(UpdateTicketTypeRequest::getId)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

        existingEvent.getTicketTypes().removeIf(existingTicketType ->
            !requestTicketTypeIds.contains(existingTicketType.getId())
        );

        Map<UUID, TicketType> existingTicketTypeIndex = existingEvent.getTicketTypes()
            .stream()
            .collect(Collectors.toMap(TicketType::getId, Function.identity()));


        for(UpdateTicketTypeRequest ticketType : request.getTicketTypes()) {
            // Create
            if(ticketType.getId() == null) {
                TicketType ticketTypeToCreate = new TicketType();
                ticketTypeToCreate.setName(ticketType.getName());
                ticketTypeToCreate.setPrice(ticketType.getPrice());
                ticketTypeToCreate.setDescription(ticketType.getDescription());
                ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
                ticketTypeToCreate.setEvent(existingEvent);
                existingEvent.getTicketTypes().add(ticketTypeToCreate);
            }
            // Update
            else if(existingTicketTypeIndex.containsKey(ticketType.getId())) {
                TicketType existingTicketType = existingTicketTypeIndex.get(ticketType.getId());
                existingTicketType.setName(ticketType.getName());
                existingTicketType.setPrice(ticketType.getPrice());
                existingTicketType.setDescription(ticketType.getDescription());
                existingTicketType.setTotalAvailable(ticketType.getTotalAvailable());
            }
            else {
                throw new TicketTypeNotFoundException("Ticket type with ID " + ticketType.getId() + " not found");
            }
        }
        return eventRepository.save(existingEvent);
    }

    @Override
    @Transactional
    public void deleteEventForOrganizer(UUID organizerId, UUID id) {
        getEventForOrganizer(organizerId, id).ifPresent(eventRepository::delete);
    }

    @Override
    public Page<Event> listPublishedEvents(Pageable pageable) {
        return eventRepository.findByStatus(EventStatusEnum.PUBLISHED, pageable);
    }

    @Override
    public Page<Event> searchPublishedEvents(String query, Pageable pageable) {
        return eventRepository.searchEvents(query, pageable);
    }

    @Override
    public Optional<Event> getPublishedEvent(UUID id) {
        return eventRepository.findByIdAndStatus(id, EventStatusEnum.PUBLISHED);
    }
}

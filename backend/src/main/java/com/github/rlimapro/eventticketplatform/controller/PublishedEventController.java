package com.github.rlimapro.eventticketplatform.controller;

import com.github.rlimapro.eventticketplatform.domain.entities.Event;
import com.github.rlimapro.eventticketplatform.dto.GetPublishedEventDetailsResponseDto;
import com.github.rlimapro.eventticketplatform.dto.ListPublishedEventResponseDto;
import com.github.rlimapro.eventticketplatform.mapper.EventMapper;
import com.github.rlimapro.eventticketplatform.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {

    private final EventMapper eventMapper;
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<Page<ListPublishedEventResponseDto>> getPublishedEvents(
        @RequestParam(required = false) String q,
        Pageable pageable
    ) {
        Page<Event> events;

        if(q != null) {
            events = eventService.searchPublishedEvents(q, pageable);
        } else {
            events = eventService.listPublishedEvents(pageable);
        }

        return ResponseEntity.ok(
            events.map(eventMapper::toListPublishedEventResponseDto)
        );
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<GetPublishedEventDetailsResponseDto> getPublishedEventDetails(
        @PathVariable UUID eventId
    ) {
        return eventService.getPublishedEvent(eventId)
            .map(eventMapper::toGetPublishedEventDetailsResponseDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}

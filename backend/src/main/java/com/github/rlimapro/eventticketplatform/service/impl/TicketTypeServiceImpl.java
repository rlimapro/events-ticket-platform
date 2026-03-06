package com.github.rlimapro.eventticketplatform.service.impl;

import com.github.rlimapro.eventticketplatform.domain.entities.Ticket;
import com.github.rlimapro.eventticketplatform.domain.entities.TicketType;
import com.github.rlimapro.eventticketplatform.domain.entities.User;
import com.github.rlimapro.eventticketplatform.domain.enums.TicketStatusEnum;
import com.github.rlimapro.eventticketplatform.exception.TicketTypeNotFoundException;
import com.github.rlimapro.eventticketplatform.exception.TicketsSoldOutException;
import com.github.rlimapro.eventticketplatform.exception.UserNotFoundException;
import com.github.rlimapro.eventticketplatform.repository.TicketRepository;
import com.github.rlimapro.eventticketplatform.repository.TicketTypeRepository;
import com.github.rlimapro.eventticketplatform.repository.UserRepository;
import com.github.rlimapro.eventticketplatform.service.QrCodeService;
import com.github.rlimapro.eventticketplatform.service.TicketTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final QrCodeService qrCodeService;

    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new UserNotFoundException("User with ID + " + userId + " not found")
        );

        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId).orElseThrow(
            () -> new TicketTypeNotFoundException("Ticket type with ID + " + ticketTypeId + " not found")
        );

        int purchasedTickets = ticketRepository.countByTicketTypeId(ticketTypeId);
        Integer totalAvailable = ticketType.getTotalAvailable();

        if(purchasedTickets + 1 > totalAvailable) {
            throw new TicketsSoldOutException();
        }

        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatusEnum.PURCHASED);
        ticket.setTicketType(ticketType);
        ticket.setPurchaser(user);

        Ticket savedTicket = ticketRepository.save(ticket);
        qrCodeService.generateQrCode(savedTicket);

        return ticketRepository.save(savedTicket);
    }
}

package com.github.rlimapro.eventticketplatform.repository;

import com.github.rlimapro.eventticketplatform.domain.entities.TicketValidation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketValidationRepository extends JpaRepository<TicketValidation, UUID> {
}

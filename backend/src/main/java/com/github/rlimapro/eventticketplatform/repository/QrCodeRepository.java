package com.github.rlimapro.eventticketplatform.repository;

import com.github.rlimapro.eventticketplatform.domain.entities.QrCode;
import com.github.rlimapro.eventticketplatform.domain.enums.QrCodeStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface QrCodeRepository extends JpaRepository<QrCode, UUID> {
    Optional<QrCode> findByTicketIdAndTicketPurchaserId(UUID ticketId, UUID ticketPurchaserId);
    Optional<QrCode> findByIdAndStatus(UUID id, QrCodeStatusEnum status);
}

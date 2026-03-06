package com.github.rlimapro.eventticketplatform.repository;

import com.github.rlimapro.eventticketplatform.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}

package com.todimu.backend.bookstoreservice.repository;

import com.todimu.backend.bookstoreservice.data.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> findByAuthorityName(String name);
}

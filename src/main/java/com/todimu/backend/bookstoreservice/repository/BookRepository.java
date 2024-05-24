package com.todimu.backend.bookstoreservice.repository;

import com.todimu.backend.bookstoreservice.data.entity.Book;
import com.todimu.backend.bookstoreservice.data.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findAllByStatus(Status status, Pageable pageable);
}

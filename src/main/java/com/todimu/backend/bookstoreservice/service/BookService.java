package com.todimu.backend.bookstoreservice.service;

import com.todimu.backend.bookstoreservice.data.dto.request.AddNewBooksRequest;
import com.todimu.backend.bookstoreservice.data.dto.request.DeleteBooksRequest;
import com.todimu.backend.bookstoreservice.data.dto.request.UpdateBookDetailsRequest;
import com.todimu.backend.bookstoreservice.data.dto.request.UpdateBookStatusRequest;
import com.todimu.backend.bookstoreservice.data.entity.Book;
import com.todimu.backend.bookstoreservice.data.enums.Status;
import com.todimu.backend.bookstoreservice.exception.NotFoundException;
import com.todimu.backend.bookstoreservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Page<Book> getListOfAvailableBooks(Pageable pageable) {
            return  bookRepository.findAllByStatus(Status.AVAILABLE, pageable);
    }

    @Transactional
    public List<String> addNewBooksToStore(AddNewBooksRequest addNewBooksRequest) {
        return addNewBooksRequest.getBooks().stream()
                .map(bookRepository::save)
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }


    @Transactional
    public Book updateBookDetails(Long bookId, UpdateBookDetailsRequest request) {
        Book bookToUpdate = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("book not found"));

        if (request.getAuthor() != null) {
            bookToUpdate.setAuthor(request.getAuthor());
        }

        if (request.getGenre() != null) {
            bookToUpdate.setGenre(request.getGenre());
        }

        if (request.getStatus() != null) {
            bookToUpdate.setStatus(request.getStatus());
        }

        return bookRepository.save(bookToUpdate);
    }

    @Transactional
    public void deleteBooksFromStore(DeleteBooksRequest request) {

        for (Long id: request.getBookIds()) {
            Optional<Book> bookOptional = bookRepository.findById(id);
            bookOptional.ifPresent(bookRepository::delete);
        }
    }

    @Transactional
    public Book changeAvailabilityStatusOfBook(UpdateBookStatusRequest request) {
        Book bookToUpdate = bookRepository.findById(request.getBookId()).orElseThrow(() -> new NotFoundException("book not found"));
        bookToUpdate.setStatus(request.getStatus());
        return bookRepository.save(bookToUpdate);
    }
}

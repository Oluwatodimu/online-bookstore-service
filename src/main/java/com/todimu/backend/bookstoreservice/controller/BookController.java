package com.todimu.backend.bookstoreservice.controller;

import com.todimu.backend.bookstoreservice.data.dto.request.AddNewBooksRequest;
import com.todimu.backend.bookstoreservice.data.dto.request.DeleteBooksRequest;
import com.todimu.backend.bookstoreservice.data.dto.request.UpdateBookDetailsRequest;
import com.todimu.backend.bookstoreservice.data.dto.request.UpdateBookStatusRequest;
import com.todimu.backend.bookstoreservice.data.dto.response.BaseResponse;
import com.todimu.backend.bookstoreservice.data.entity.Book;
import com.todimu.backend.bookstoreservice.service.BookService;
import com.todimu.backend.bookstoreservice.utils.Utils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/books", produces = {MediaType.APPLICATION_JSON_VALUE})
public class BookController {

    private final BookService bookService;

    @GetMapping(path = "/available")
    public ResponseEntity<BaseResponse> getAvailableBooks(
            @RequestParam(required = false) Integer pageNumber,
                @RequestParam(required = false) Integer pageSize) {

        log.info("getting all available books");
        Pageable pageable = Utils.createSortedPageableObject(pageNumber, pageSize);
        Page<Book> availableBooks = bookService.getListOfAvailableBooks(pageable);
        return ResponseEntity.ok(Utils.createBseResponse(availableBooks));
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> addNewBooks(@Valid @RequestBody AddNewBooksRequest request) {
        log.info("adding the following books to store: {}", request.getBooks().toString());
        List<String> addedBookTitles = bookService.addNewBooksToStore(request);
        return new ResponseEntity<>(Utils.createBseResponse(addedBookTitles), HttpStatus.CREATED);
    }

    @PatchMapping("/update/{bookId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> updateBookDetails(
            @PathVariable(name = "bookId") @NotNull Long bookId, @Valid @RequestBody UpdateBookDetailsRequest request) {

        log.info("updating book details of book: {}", bookId);
        Book updatedBook = bookService.updateBookDetails(bookId, request);
        return ResponseEntity.ok(Utils.createBseResponse(updatedBook));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> deleteBookFromStore(@RequestBody @Valid DeleteBooksRequest request) {
        log.info("deleting book with ids: {}", request.getBookIds().toString());
        bookService.deleteBooksFromStore(request);
        return new ResponseEntity<>(Utils.createBseResponse(null), HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/status")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> changeBookAvailabilityStatus(@Valid @RequestBody UpdateBookStatusRequest request) {
        log.info("change book status for book: {} to {}", request.getBookId(), request.getStatus());
        Book bookToUpdate = bookService.changeAvailabilityStatusOfBook(request);
        return ResponseEntity.ok(Utils.createBseResponse(bookToUpdate));
    }
}

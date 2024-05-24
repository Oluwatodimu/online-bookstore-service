package com.todimu.backend.bookstoreservice.service;

import com.todimu.backend.bookstoreservice.data.dto.request.AddNewBooksRequest;
import com.todimu.backend.bookstoreservice.data.dto.request.DeleteBooksRequest;
import com.todimu.backend.bookstoreservice.data.dto.request.UpdateBookDetailsRequest;
import com.todimu.backend.bookstoreservice.data.dto.request.UpdateBookStatusRequest;
import com.todimu.backend.bookstoreservice.data.entity.Book;
import com.todimu.backend.bookstoreservice.data.enums.Status;
import com.todimu.backend.bookstoreservice.exception.NotFoundException;
import com.todimu.backend.bookstoreservice.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock private BookRepository bookRepository;

    @InjectMocks private BookService bookService;

    @Test
    public void getListOfAvailableBooks_ReturnsPageOfBooks() {
        // Given
        Page<Book> expectedPage = new PageImpl<>(Arrays.asList(new Book(), new Book()));
        BDDMockito.given(bookRepository.findAllByStatus(Status.AVAILABLE, Pageable.unpaged())).willReturn(expectedPage);

        // When
        Page<Book> result = bookService.getListOfAvailableBooks(Pageable.unpaged());

        // Then
        assertThat(result).isEqualTo(expectedPage);
    }

    @Test
    public void addNewBooksToStore_ReturnsListOfTitles() {
        // Given
        AddNewBooksRequest request = new AddNewBooksRequest();
        request.setBooks(Arrays.asList(new Book(), new Book()));
        BDDMockito.given(bookRepository.save(Mockito.any(Book.class))).willReturn(new Book());

        // When
        List<String> result = bookService.addNewBooksToStore(request);

        // Then
        Assertions.assertThat(result.size() == 2);
    }

    @Test
    void updateBookDetails_UpdatesBookAnd_ReturnsUpdatedBook() {
        // Given
        UpdateBookDetailsRequest request = new UpdateBookDetailsRequest();
        request.setAuthor("New Author");
        Book existingBook = new Book();
        existingBook.setAuthor("Old Author");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(Mockito.any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Book result = bookService.updateBookDetails(1L, request);

        // Then
        assertThat(result.getAuthor()).isEqualTo("New Author");
    }

    @Test
    void deleteBooksFromStore_DeletesBooks() {
        // Given
        DeleteBooksRequest request = new DeleteBooksRequest();
        request.setBookIds(Arrays.asList(1L, 2L));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(new Book()));
        when(bookRepository.findById(2L)).thenReturn(Optional.of(new Book()));

        // When
        bookService.deleteBooksFromStore(request);

        // Then
        verify(bookRepository, Mockito.times(2)).delete(Mockito.any());
    }

    @Test
    void changeAvailabilityStatusOfBook_UpdatesBookStatusAndReturnsUpdatedBook() {
        // Given
        UpdateBookStatusRequest request = new UpdateBookStatusRequest(1L, Status.OUT_OF_STOCK);
        Book existingBook = new Book();
        existingBook.setStatus(Status.AVAILABLE);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(Mockito.any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Book result = bookService.changeAvailabilityStatusOfBook(request);

        // Then
        assertThat(result.getStatus()).isEqualTo(Status.OUT_OF_STOCK);
    }

    @Test
    void updateBookDetails_ThrowsNotFoundException_WhenBookNotFound() {
        // Given
        UpdateBookDetailsRequest request = new UpdateBookDetailsRequest();
        request.setAuthor("New Author");
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // When, Then
        NotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class,
                () -> bookService.updateBookDetails(1L, request));
        assertThat(exception.getMessage()).isEqualTo("book not found");
    }

    @Test
    void changeAvailabilityStatusOfBook_ThrowsNotFoundException_WhenBookNotFound() {
        // Given
        UpdateBookStatusRequest request = new UpdateBookStatusRequest(1L, Status.OUT_OF_STOCK);
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // When, Then
        NotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class,
                () -> bookService.changeAvailabilityStatusOfBook(request));
        assertThat(exception.getMessage()).isEqualTo("book not found");
    }
}

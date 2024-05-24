package com.todimu.backend.bookstoreservice.data.dto.request;

import com.todimu.backend.bookstoreservice.data.entity.Book;
import lombok.Data;

import java.util.List;

@Data
public class AddNewBooksRequest {

    private List<Book> books;
}

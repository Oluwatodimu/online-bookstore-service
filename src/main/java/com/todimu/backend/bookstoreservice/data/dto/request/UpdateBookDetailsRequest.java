package com.todimu.backend.bookstoreservice.data.dto.request;

import com.todimu.backend.bookstoreservice.data.enums.Genre;
import com.todimu.backend.bookstoreservice.data.enums.Status;
import lombok.Data;

@Data
public class UpdateBookDetailsRequest {

    private String author;
    private Genre genre;
    private Status status;
}

package com.todimu.backend.bookstoreservice.data.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class DeleteBooksRequest {

    private List<Long> bookIds;
}

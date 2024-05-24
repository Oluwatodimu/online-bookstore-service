package com.todimu.backend.bookstoreservice.data.dto.request;

import com.todimu.backend.bookstoreservice.data.enums.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateBookStatusRequest {

    @NotNull(message = "id cannot be null")
    private Long bookId;

    @NotNull(message = "status cannot be empty")
    private Status status;
}

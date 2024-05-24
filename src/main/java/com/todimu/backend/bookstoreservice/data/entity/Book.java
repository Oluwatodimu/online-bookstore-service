package com.todimu.backend.bookstoreservice.data.entity;

import com.todimu.backend.bookstoreservice.data.enums.Genre;
import com.todimu.backend.bookstoreservice.data.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class Book extends BaseEntity {

    @NotEmpty
    @Column(updatable = false)
    private String title;

    @NotEmpty
    private String author;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;
}

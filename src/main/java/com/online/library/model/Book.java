package com.online.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "books")
public class Book extends AuditBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String isbn;

    private Integer revisionNumber;

    private LocalDate publishedDate;

    private String publisher;

    @Column(columnDefinition = "text")
    private String authors;

    @Column(nullable = false)
    private LocalDate dateAdded;

    private String genre;
    private String coverImageUrl;

    @Column(nullable = false)
    private Integer availableCopies;
}

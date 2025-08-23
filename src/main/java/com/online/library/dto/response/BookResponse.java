package com.online.library.dto.response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BookResponse {
    private Long bookId;
    private String title;
    private String isbn;
    private Integer revisionNumber;
    private LocalDate publishedDate;
    private String publisher;
    private String authors;
    private LocalDate dateAdded;
    private String genre;
    private String coverImageUrl;
    private Integer availableCopies;
}


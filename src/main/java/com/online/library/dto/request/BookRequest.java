package com.online.library.dto.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BookRequest {
    private String title;
    private String isbn;
    private Integer revisionNumber;
    private LocalDate publishedDate;
    private String publisher;
    private String authors;
    private String genre;
    private String coverImageUrl;
    private Integer availableCopies;
}

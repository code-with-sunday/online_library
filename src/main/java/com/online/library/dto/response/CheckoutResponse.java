package com.online.library.dto.response;

import lombok.Data;

import java.time.Instant;

@Data
public class CheckoutResponse {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private Long readerId;
    private String readerEmail;
    private Instant checkedOutAt;
    private Instant dueAt;
    private Instant checkedInAt;
    private String status;

}
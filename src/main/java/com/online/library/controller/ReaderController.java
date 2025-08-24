package com.online.library.controller;

import com.online.library.dto.response.BookResponse;
import com.online.library.dto.response.CheckoutResponse;
import com.online.library.service.BookService;
import com.online.library.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reader")
public class ReaderController {

    private final BookService bookService;
    private final CheckoutService checkoutService;

    @GetMapping("/book/search")
    public ResponseEntity<Page<BookResponse>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate addedFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate addedTo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateAdded") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()
        );

        Page<BookResponse> response = bookService.search(title, isbn, publisher, addedFrom, addedTo, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/book")
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        List<BookResponse> response = bookService.getAllBooks();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/checkout/{bookId}")
    public ResponseEntity<CheckoutResponse> checkout(@PathVariable Long bookId) {
        CheckoutResponse response = checkoutService.checkout(bookId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/checkin/{checkoutId}")
    public ResponseEntity<CheckoutResponse> checkin(@PathVariable Long checkoutId) {
        CheckoutResponse response = checkoutService.checkin(checkoutId);
        return ResponseEntity.ok(response);
    }

}

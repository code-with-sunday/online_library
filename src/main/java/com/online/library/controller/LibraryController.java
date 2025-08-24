package com.online.library.controller;

import com.online.library.dto.request.BookRequest;
import com.online.library.dto.request.CheckoutRequest;
import com.online.library.dto.response.BookResponse;
import com.online.library.dto.response.CheckoutResponse;
import com.online.library.service.BookService;
import com.online.library.service.CheckoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library")
public class LibraryController {

    private final BookService bookService;
    private final CheckoutService checkoutService;


    @PostMapping("/book")
    public ResponseEntity<BookResponse> createBook(@RequestBody @Valid BookRequest bookRequest) {
        BookResponse response = bookService.createBook(bookRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/book")
    public ResponseEntity<BookResponse> updateBook(@RequestBody @Valid BookRequest bookRequest) {
        BookResponse response = bookService.saveOrUpdateBook(bookRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/checkout/update")
    public ResponseEntity<CheckoutResponse> saveOrUpdate(@RequestBody CheckoutRequest request) {
        CheckoutResponse response = checkoutService.saveOrUpdateCheckout(request);
        return ResponseEntity.ok(response);
    }
}
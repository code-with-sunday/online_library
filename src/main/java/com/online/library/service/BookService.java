package com.online.library.service;

import com.online.library.common.BookSpecification;
import com.online.library.dto.request.BookRequest;
import com.online.library.dto.response.BookResponse;
import com.online.library.exception.BookAlreadyExistException;
import com.online.library.model.Book;
import com.online.library.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public BookResponse createBook(BookRequest request) {
        if (bookRepository.findByIsbn(request.getIsbn()).isPresent()) {
            throw new BookAlreadyExistException("Book with ISBN already exists: "+ request.getIsbn());
        }

        Book book = mapToBook(request);
        Book savedBook = bookRepository.save(book);

        return mapToBookResponse(savedBook);
    }

    public Page<BookResponse> search(String title, String isbn, String publisher,
                                     LocalDate addedFrom, LocalDate addedTo, Pageable pageable) {

        Specification<Book> spec = Specification
                .where(BookSpecification.hasTitle(title))
                .and(BookSpecification.hasIsbn(isbn))
                .and(BookSpecification.hasPublisher(publisher))
                .and(BookSpecification.addedBetween(addedFrom, addedTo));

        Page<Book> bookPage = bookRepository.findAll(spec, pageable);

        return bookPage.map(this::mapToBookResponse);
    }

    public List<BookResponse> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(this::mapToBookResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookResponse saveOrUpdateBook(BookRequest request) {
        Book book = bookRepository.findByIsbn(request.getIsbn())
                .map(existing -> {
                    existing.setTitle(request.getTitle());
                    existing.setRevisionNumber(request.getRevisionNumber());
                    existing.setPublishedDate(request.getPublishedDate());
                    existing.setPublisher(request.getPublisher());
                    existing.setAuthors(request.getAuthors());
                    existing.setGenre(request.getGenre());
                    existing.setCoverImageUrl(request.getCoverImageUrl());
                    existing.setAvailableCopies(request.getAvailableCopies());
                    return existing;
                })
                .orElse(mapToBook(request));

        Book savedBook = bookRepository.save(book);
        return mapToBookResponse(savedBook);
    }

    public Book mapToBook(BookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setRevisionNumber(request.getRevisionNumber());
        book.setPublishedDate(request.getPublishedDate());
        book.setPublisher(request.getPublisher());
        book.setAuthors(request.getAuthors());
        book.setGenre(request.getGenre());
        book.setCoverImageUrl(request.getCoverImageUrl());
        book.setAvailableCopies(request.getAvailableCopies());

        book.setDateAdded(LocalDate.now());

        return book;
    }

    public BookResponse mapToBookResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setBookId(book.getBookId());
        response.setTitle(book.getTitle());
        response.setIsbn(book.getIsbn());
        response.setRevisionNumber(book.getRevisionNumber());
        response.setPublishedDate(book.getPublishedDate());
        response.setPublisher(book.getPublisher());
        response.setAuthors(book.getAuthors());
        response.setDateAdded(book.getDateAdded());
        response.setGenre(book.getGenre());
        response.setCoverImageUrl(book.getCoverImageUrl());
        response.setAvailableCopies(book.getAvailableCopies());

        return response;
    }
}

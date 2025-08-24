package com.online.library.service;

import com.online.library.common.CheckoutStatus;
import com.online.library.dto.request.CheckoutRequest;
import com.online.library.dto.response.CheckoutResponse;
import com.online.library.exception.GeneralArgumentException;
import com.online.library.exception.UnAuthorizedException;
import com.online.library.exception.UserNotFoundException;
import com.online.library.model.Book;
import com.online.library.model.Checkout;
import com.online.library.model.User;
import com.online.library.repository.BookRepository;
import com.online.library.repository.CheckoutRepository;
import com.online.library.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckoutService {

    private final CheckoutRepository checkoutRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    private static final int MAX_ACTIVE = 5;

    @Transactional
    public CheckoutResponse checkout(Long bookId) {

        String email = checkAuthentication();

        log.info("Checkout email from authentication: " + email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found " + email));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalStateException("Book not found"));

        if (book.getAvailableCopies() <= 0) {
            throw new GeneralArgumentException("Book not available");
        }

        if (checkoutRepository.countByReader_IdAndStatus(user.getId(), CheckoutStatus.ACTIVE) >= MAX_ACTIVE) {
            throw new GeneralArgumentException("Active checkout limit reached");
        }

        if (checkoutRepository.existsByBook_IdAndReader_IdAndStatus(book.getBookId(), user.getId(), CheckoutStatus.ACTIVE)) {
            throw new GeneralArgumentException("You already have this book checked out");
        }

        Checkout myCheckOut = new Checkout();
        myCheckOut.setBook(book);
        myCheckOut.setReader(user);
        myCheckOut.setCheckedOutAt(Instant.now());
        myCheckOut.setDueAt(Instant.now().plus(10, ChronoUnit.DAYS));
        myCheckOut.setStatus(CheckoutStatus.ACTIVE);

        checkoutRepository.save(myCheckOut);

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        return mapToCheckoutResponse(myCheckOut);
    }

    @Transactional
    public CheckoutResponse checkin(Long checkoutId) {

        String email = checkAuthentication();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found " + email));

        Checkout myCheckOut = checkoutRepository.findByIdAndReader_Id(checkoutId, user.getId()).orElseThrow();

        if (myCheckOut.getStatus() != CheckoutStatus.ACTIVE) {
            throw new GeneralArgumentException("Not active");
        }

        myCheckOut.setCheckedInAt(Instant.now());
        myCheckOut.setStatus(CheckoutStatus.RETURNED);

        Book book = myCheckOut.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);

        return mapToCheckoutResponse(myCheckOut);
    }

    public static Checkout mapToCheckout(CheckoutRequest dto, Book book, User reader) {
        Checkout checkout = new Checkout();
        checkout.setBook(book);
        checkout.setReader(reader);
        checkout.setCheckedOutAt(dto.getCheckedOutAt());
        checkout.setDueAt(dto.getDueAt());
        checkout.setStatus(dto.getStatus());
        return checkout;
    }

    public static CheckoutResponse mapToCheckoutResponse(Checkout checkout) {
        CheckoutResponse response = new CheckoutResponse();
        response.setId(checkout.getId());
        response.setBookId(checkout.getBook().getBookId());
        response.setBookTitle(checkout.getBook().getTitle());
        response.setReaderId(checkout.getReader().getId());
        response.setReaderEmail(checkout.getReader().getEmail());
        response.setCheckedOutAt(checkout.getCheckedOutAt());
        response.setDueAt(checkout.getDueAt());
        response.setCheckedInAt(checkout.getCheckedInAt());
        response.setStatus(checkout.getStatus().name());
        return response;
    }

    @Transactional
    public CheckoutResponse saveOrUpdateCheckout(CheckoutRequest request) {
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        User reader = userRepository.findById(request.getReaderId())
                .orElseThrow(() -> new RuntimeException("Reader not found"));

        Checkout checkout = mapToCheckout(request, book, reader);

        Checkout existing = checkoutRepository
                .findByBookAndReader(book, reader)
                .orElse(null);

        if (existing != null) {
            existing.setCheckedOutAt(request.getCheckedOutAt());
            existing.setDueAt(request.getDueAt());
            existing.setStatus(request.getStatus());
            checkout = checkoutRepository.save(existing);
        } else {
            checkout = checkoutRepository.save(checkout);
        }

        return mapToCheckoutResponse(checkout);
    }

    public String checkAuthentication(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new UnAuthorizedException("Session timed-Out, please login first");
        }

        return auth.getName();
    }
}

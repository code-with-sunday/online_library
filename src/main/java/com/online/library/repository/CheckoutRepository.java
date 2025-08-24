package com.online.library.repository;

import com.online.library.common.CheckoutStatus;
import com.online.library.model.Book;
import com.online.library.model.Checkout;
import com.online.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    long countByReader_IdAndStatus(Long readerId, CheckoutStatus status);

    Optional<Checkout> findByIdAndReader_Id(Long id, Long readerId);

    List<Checkout> findByStatusAndDueAtBetween(CheckoutStatus status, Instant from, Instant to);

    List<Checkout> findByStatusAndDueAtBefore(CheckoutStatus status, Instant cutoff);

    List<Checkout> findByStatus(CheckoutStatus status);

    boolean existsByBook_IdAndReader_IdAndStatus(Long bookId, Long readerId, CheckoutStatus status);

    Optional<Checkout> findByBookAndReader(Book book, User reader);
}
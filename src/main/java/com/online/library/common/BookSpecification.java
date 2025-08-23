package com.online.library.common;

import com.online.library.model.Book;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class BookSpecification {

    public static Specification<Book> hasTitle(String title) {
        return (root, query, cb) -> title == null ? null :
                cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Book> hasIsbn(String isbn) {
        return (root, query, cb) -> isbn == null ? null :
                cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Book> hasPublisher(String publisher) {
        return (root, query, cb) -> publisher == null ? null :
                cb.like(cb.lower(root.get("publisher")), "%" + publisher.toLowerCase() + "%");
    }

    public static Specification<Book> addedBetween(LocalDate addedFrom, LocalDate addedTo) {
        return (root, query, cb) -> {
            if (addedFrom != null && addedTo != null) {
                return cb.between(root.get("dateAdded"), addedFrom, addedTo);
            } else if (addedFrom != null) {
                return cb.greaterThanOrEqualTo(root.get("dateAdded"), addedFrom);
            } else if (addedTo != null) {
                return cb.lessThanOrEqualTo(root.get("dateAdded"), addedTo);
            }
            return null;
        };
    }
}

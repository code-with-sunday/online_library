package com.online.library.model;

import com.online.library.common.CheckoutStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "check_out")
public class Checkout extends AuditBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(optional = false) @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(optional = false) @JoinColumn(name = "reader_id")
    private User reader;

    @Column(nullable = false)
    private Instant checkedOutAt;

    @Column(nullable = false)
    private Instant dueAt;

    private Instant checkedInAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CheckoutStatus status;

}

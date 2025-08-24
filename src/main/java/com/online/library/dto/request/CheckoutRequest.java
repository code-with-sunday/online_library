package com.online.library.dto.request;

import com.online.library.common.CheckoutStatus;
import lombok.Data;

import java.time.Instant;

@Data
public class CheckoutRequest {
    private Long bookId;
    private Long readerId;
    private Instant checkedOutAt;
    private Instant dueAt;
    private CheckoutStatus status;

}

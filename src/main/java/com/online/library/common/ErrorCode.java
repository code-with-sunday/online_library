package com.online.library.common;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public final class ErrorCode {

    public static final String PREFIX = "LIB-";

    public static String fromStatus(HttpStatus statusCode) {
        return PREFIX + statusCode.value();
    }
}

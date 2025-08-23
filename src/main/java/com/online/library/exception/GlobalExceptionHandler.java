package com.online.library.exception;

import com.online.library.common.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final Map<Class<? extends Exception>, HttpStatus> EXCEPTION_STATUS_MAP = new HashMap<>();

    static {
        EXCEPTION_STATUS_MAP.put(UnAuthorizedException.class, HttpStatus.UNAUTHORIZED);
        EXCEPTION_STATUS_MAP.put(UserNotFoundException.class, HttpStatus.NOT_FOUND);
        EXCEPTION_STATUS_MAP.put(UserAlreadyExistException.class, HttpStatus.CONFLICT);
        EXCEPTION_STATUS_MAP.put(IllegalArgumentException.class, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleExceptions(RuntimeException ex, HttpServletRequest request) {
        HttpStatus status = EXCEPTION_STATUS_MAP.entrySet().stream()
                .filter(entry -> entry.getKey().isAssignableFrom(ex.getClass()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(HttpStatus.INTERNAL_SERVER_ERROR);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());

        problemDetail.setProperty("timestamp", Instant.now().toString());
        problemDetail.setProperty("errorCode", ErrorCode.fromStatus(status));
        problemDetail.setProperty("path", request.getRequestURI());

        log.info("""
                        Exception handled:
                        - Type: {}
                        - Message: {}
                        - Status: {}
                        - ErrorCode: {}
                        - Path: {}
                        - Timestamp: {}
                        """,
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                status,
                ErrorCode.fromStatus(status),
                request.getRequestURI(),
                Instant.now().toString()
        );

        return problemDetail;
    }
}

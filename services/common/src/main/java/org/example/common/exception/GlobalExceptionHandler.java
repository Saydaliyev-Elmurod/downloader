package org.example.common.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.common.exception.exp.*;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ServerWebExchange;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger();

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({
            ApiException.class,
            AlreadyExistsException.class,
            DateTimeParseException.class,
            UnsupportedOperationException.class,
            IllegalArgumentException.class,
            IllegalStateException.class,
            NullPointerException.class,
            RuntimeException.class,
            InvalidArgumentException.class,
            InvalidOperationException.class
    })
    public ResponseEntity<?> handleBadRequests(final RuntimeException e, final ServerWebExchange exchange) {
        return constructExceptionResponse(e, exchange, BAD_REQUEST);
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler({
            ForbiddenException.class,
            CreateForbiddenException.class,
            DeleteForbiddenException.class,
            ListForbiddenException.class,
            RetrieveForbiddenException.class,
            UpdateForbiddenException.class,
            UserBlockedException.class
    })
    public final ResponseEntity<?> handleForbiddenException(
            final ForbiddenException e, final ServerWebExchange exchange) {
        return constructExceptionResponse(e, exchange, FORBIDDEN);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleException(final Exception e, final ServerWebExchange exchange) {
        return constructExceptionResponse(e, exchange, INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOException(final IOException e, final ServerWebExchange exchange) {
        return constructExceptionResponse(e, exchange, INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler({JsonParsingException.class, DecodingException.class})
    public final ResponseEntity<?> handleJsonException(final Exception e, final ServerWebExchange exchange) {
        return constructExceptionResponse(e, exchange, UNPROCESSABLE_ENTITY);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler({NotFoundException.class, NoSuchElementException.class})
    public ResponseEntity<?> handleNotFoundException(
            final NoSuchElementException e, final ServerWebExchange exchange) {
        return constructExceptionResponse(e, exchange, NOT_FOUND);
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler({
            InvalidCredentialsException.class,
            InvalidTokenException.class,
            UnauthorizedException.class,
    })
    public final ResponseEntity<?> handleUnauthorizedException(
            final UnauthorizedException e, final ServerWebExchange exchange) {
        return constructExceptionResponse(e, exchange, UNAUTHORIZED);
    }

    @ExceptionHandler({UnauthorizedExceptionYandex.class})
    public final ResponseEntity<?> handleUnauthorizedException2(
            final UnauthorizedException e, final ServerWebExchange exchange) {
        return constructExceptionResponse(e, exchange, UNAUTHORIZED);
    }

    @ResponseStatus(SERVICE_UNAVAILABLE)
    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<?> handleServiceUnavailableException(
            final ServiceUnavailableException e, final ServerWebExchange exchange) {
        return constructExceptionResponse(e, exchange, SERVICE_UNAVAILABLE);
    }

    protected ResponseEntity<ExceptionResponse> constructExceptionResponse(
            final Exception e, final ServerWebExchange exchange, final HttpStatus status) {
        final String path = exchange.getRequest().getPath().toString();

        LOGGER.error("Failed to request [{}] path. Error:", path, e);
        return new ResponseEntity<>(
                new ExceptionResponse(e, path, status), status);
    }
}

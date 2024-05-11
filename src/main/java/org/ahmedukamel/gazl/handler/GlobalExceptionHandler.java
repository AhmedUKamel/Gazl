package org.ahmedukamel.gazl.handler;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.gazl.constant.LocaleConstants;
import org.ahmedukamel.gazl.dto.api.ApiResponse;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Function<Exception, ApiResponse> function = exception -> new ApiResponse(false, exception.getLocalizedMessage(), "");

    final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException exception, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        return ResponseEntity.status(status).body(new ApiResponse(false, exception.getMessage().split(":")[0], ""));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException exception, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        List<String> data = exception.getFieldErrors().stream().map(i -> "%s: %s".formatted(i.getField(), i.getDefaultMessage())).toList();
        return ResponseEntity.status(status).body(new ApiResponse(false, "Invalid Request Body.", data));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(@NonNull MissingServletRequestParameterException exception, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        return ResponseEntity.status(status).body(function.apply(exception));
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(@NonNull HandlerMethodValidationException exception, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        return ResponseEntity.status(status).body(new ApiResponse(false, "Invalid Request Parameter(s).", ""));
    }

    @ExceptionHandler(value = AuthenticationException.class)
    protected ResponseEntity<Object> handleAuthenticationException(@NotNull AuthenticationException exception) {
        return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(function.apply(exception));
    }

    @ExceptionHandler(value = DataAccessException.class)
    protected ResponseEntity<Object> handleDataAccessException(DataAccessException exception) {
        return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(function.apply(exception));
    }

    @ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(@NotNull RuntimeException exception) {
        return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(function.apply(exception));
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentialsException() {
        String message = messageSource.getMessage(
                "org.ahmedukamel.gazl.handler.GlobalExceptionHandler.handleBadCredentialsException", null, LocaleConstants.ARABIC);
        return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(new ApiResponse(false, message, ""));
    }

    @ExceptionHandler(value = DisabledException.class)
    protected ResponseEntity<Object> handleDisabledException() {
        String message = messageSource.getMessage(
                "org.ahmedukamel.gazl.handler.GlobalExceptionHandler.handleDisabledException", null, LocaleConstants.ARABIC);
        return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(new ApiResponse(false, message, ""));
    }

    @ExceptionHandler(value = LockedException.class)
    protected ResponseEntity<Object> handleLockedException() {
        String message = messageSource.getMessage(
                "org.ahmedukamel.gazl.handler.GlobalExceptionHandler.handleLockedException", null, LocaleConstants.ARABIC);
        return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(new ApiResponse(false, message, ""));
    }

    @ExceptionHandler(value = IOException.class)
    protected ResponseEntity<Object> handleIOException(IOException exception) {
        return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(function.apply(exception));
    }
}
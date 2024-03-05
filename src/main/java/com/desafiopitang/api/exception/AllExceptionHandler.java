package com.desafiopitang.api.exception;

import com.desafiopitang.api.dto.ErroDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Locale;

@RestControllerAdvice
public class AllExceptionHandler  {

    @ExceptionHandler({BadCredentialsException.class, InternalAuthenticationServiceException.class})
    public ResponseEntity<ErroDTO> authenticationException(Exception ex, Locale locale) {

        ErroDTO erroDTO = new ErroDTO("Invalid login or password", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erroDTO);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroDTO> entityDuplicate(DataIntegrityViolationException exception) {
        ErroDTO erroDTO = new ErroDTO(exception.getMessage(),String.valueOf(HttpStatus.BAD_REQUEST.value()));
        return ResponseEntity.badRequest().body(erroDTO);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroDTO>  entityNotFound(EntityNotFoundException exception) {
        ErroDTO erroDTO = new ErroDTO(exception.getMessage(),String.valueOf(HttpStatus.NOT_FOUND.value()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroDTO);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErroDTO>  businessException(BusinessException exception) {
        ErroDTO erroDTO = new ErroDTO(exception.getMessage(), String.valueOf(exception.getStatus().value()));
        return ResponseEntity.status(exception.getStatus()).body(erroDTO);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroDTO> exception(Exception exception) {
        ErroDTO erroDTO = new ErroDTO(exception.getMessage(), String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        return ResponseEntity.internalServerError().body(erroDTO);
    }
}


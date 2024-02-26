package com.desafiopitang.api.exception;

import com.desafiopitang.api.dto.ErroDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AllExceptionHandler  {

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
        HttpStatus status = null;
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


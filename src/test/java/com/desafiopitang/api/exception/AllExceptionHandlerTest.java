package com.desafiopitang.api.exception;

import com.desafiopitang.api.dto.ErroDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class AllExceptionHandlerTest {

    @InjectMocks
    AllExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Resgistro já cadastrado.")
    void entityDuplicate() {

        ResponseEntity<ErroDTO> response = exceptionHandler.entityDuplicate(new DataIntegrityViolationException("Resgistro já existe cadastrado."));

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ErroDTO.class, response.getBody().getClass());

    }

    @Test
    @DisplayName("Entidade não encontrada.")
    void entityNotFound() {
        ResponseEntity<ErroDTO> response = exceptionHandler.entityNotFound(new EntityNotFoundException("Entidade não encontrada"));

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ErroDTO.class, response.getBody().getClass());
    }


}
package com.desafiopitang.api.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public HttpStatus getStatus() {
        return status;
    }


    public void setStatus(HttpStatus status) {
        this.status = status;
    }


    private HttpStatus status;

    public BusinessException(String message) {
        super(message);
    }


    public BusinessException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}


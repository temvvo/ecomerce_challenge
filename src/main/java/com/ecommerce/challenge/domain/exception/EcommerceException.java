package com.ecommerce.challenge.domain.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class EcommerceException extends Exception {
    @Serial
    private static final long serialVersionUID = 1688086813426024787L;

    private final int status;
    private final String message;

    public EcommerceException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

}
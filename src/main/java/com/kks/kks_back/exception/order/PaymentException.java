package com.kks.kks_back.exception.order;

public class PaymentException extends RuntimeException {
    public PaymentException(String message) {
        super(message);
    }
}

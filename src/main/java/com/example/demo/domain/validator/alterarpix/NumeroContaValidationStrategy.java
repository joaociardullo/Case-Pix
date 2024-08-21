package com.example.demo.domain.validator.alterarpix;

import com.example.demo.domain.request.PixChaveRequest;
import com.example.demo.exceptions.ResourceNotFoundException;

public class NumeroContaValidationStrategy implements ValidationStrategy {
    @Override
    public void validate(PixChaveRequest request) {
        String numeroConta = Integer.toString(request.getNumeroConta());
        if (!numeroConta.matches("\\d{8}")) {
            throw new ResourceNotFoundException("Tipo de numero conta invalida");
        }
    }
}

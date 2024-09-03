package com.example.demo.core.domain.validator.alterarpix;

import com.example.demo.core.domain.request.PixChaveRequestDTO;
import com.example.demo.core.exceptions.ResourceNotFoundException;

public class NumeroContaValidationStrategy implements ValidationStrategy {
    @Override
    public void validate(PixChaveRequestDTO request) {
        String numeroConta = Integer.toString(request.getNumeroConta());
        if (!numeroConta.matches("\\d{8}")) {
            throw new ResourceNotFoundException("Tipo de numero conta invalida");
        }
    }
}

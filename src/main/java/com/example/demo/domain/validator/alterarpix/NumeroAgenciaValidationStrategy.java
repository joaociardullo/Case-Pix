package com.example.demo.domain.validator.alterarpix;

import com.example.demo.domain.request.PixChaveRequest;
import com.example.demo.exceptions.ResourceNotFoundException;

public class NumeroAgenciaValidationStrategy implements ValidationStrategy {
    @Override
    public void validate(PixChaveRequest request) {
        String numeroAgencia = Integer.toString(request.getNumeroAgencia());
        if (!numeroAgencia.matches("\\d{4}")) {
            throw new ResourceNotFoundException("Tipo de agencia invalida");
        }
    }
}
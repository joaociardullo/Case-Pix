package com.example.demo.core.domain.validator.alterarpix;

import com.example.demo.core.domain.request.PixChaveRequestDTO;
import com.example.demo.core.exceptions.ResourceNotFoundException;

public class NumeroAgenciaValidationStrategy implements ValidationStrategy {
    @Override
    public void validate(PixChaveRequestDTO request) {
        String numeroAgencia = Integer.toString(request.getNumeroAgencia());
        if (!numeroAgencia.matches("\\d{4}")) {
            throw new ResourceNotFoundException("Tipo de agencia invalida");
        }
    }
}
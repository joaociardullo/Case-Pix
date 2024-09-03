package com.example.demo.core.domain.validator.alterarpix;

import com.example.demo.core.domain.request.PixChaveRequestDTO;
import com.example.demo.core.exceptions.ResourceNotFoundException;

import java.util.Arrays;

public class TipoContaValidationStrategy implements ValidationStrategy {
    @Override
    public void validate(PixChaveRequestDTO request) {
        if (request.getTipoConta() == null || !Arrays.asList("corrente", "poupança").contains(request.getTipoConta())) {
            throw new ResourceNotFoundException("Tipo de conta inválido");
        }
    }
}

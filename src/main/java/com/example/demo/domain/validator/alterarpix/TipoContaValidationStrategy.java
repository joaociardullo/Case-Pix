package com.example.demo.domain.validator.alterarpix;

import com.example.demo.domain.request.PixChaveRequest;
import com.example.demo.exceptions.ResourceNotFoundException;

import java.util.Arrays;

public class TipoContaValidationStrategy implements ValidationStrategy {
    @Override
    public void validate(PixChaveRequest request) {
        if (request.getTipoConta() == null || !Arrays.asList("corrente", "poupança").contains(request.getTipoConta())) {
            throw new ResourceNotFoundException("Tipo de conta inválido");
        }
    }
}

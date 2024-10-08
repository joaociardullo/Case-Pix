package com.example.demo.core.domain.validator.cadastarPix;

import com.example.demo.core.exceptions.ResourceNotFoundException;

public class CpfValidator implements TipoChaveValidator {
    @Override
    public void validate(String valorChave) {
        if (valorChave.length() != 11 || !valorChave.matches("\\d+")) {
            throw new ResourceNotFoundException("CPF não segue o padrao");
        }
    }
}

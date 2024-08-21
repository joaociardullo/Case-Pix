package com.example.demo.domain.validator.cadastarPix;

import com.example.demo.exceptions.ResourceNotFoundException;

public class CelularValidator implements TipoChaveValidator {
    @Override
    public void validate(String valorChave) {
        if (!valorChave.startsWith("+") || valorChave.length() != 14) {
            throw new ResourceNotFoundException("Celular nao segue o padrao");
        }
    }
}

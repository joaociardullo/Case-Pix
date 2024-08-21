package com.example.demo.core.repository.domain.validator.cadastarPix;

import com.example.demo.core.repository.exceptions.ResourceNotFoundException;

public class EmailValidator implements TipoChaveValidator {
    @Override
    public void validate(String valorChave) {
        if (!valorChave.contains("@") || valorChave.length() > 77) {
            throw new ResourceNotFoundException("email não segue o padrao");
        }
    }
}

package com.example.demo.domain.validator.cadastarPix;

import com.example.demo.exceptions.ResourceNotFoundException;

public class EmailValidator implements TipoChaveValidator {
    @Override
    public void validate(String valorChave) {
        if (!valorChave.contains("@") || valorChave.length() > 77) {
            throw new ResourceNotFoundException("email não segue o padrao");
        }
    }
}

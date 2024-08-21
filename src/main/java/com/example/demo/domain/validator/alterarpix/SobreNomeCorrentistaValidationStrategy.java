package com.example.demo.domain.validator.alterarpix;

import com.example.demo.domain.request.PixChaveRequest;
import com.example.demo.exceptions.ResourceNotFoundException;

public class SobreNomeCorrentistaValidationStrategy implements ValidationStrategy {
    @Override
    public void validate(PixChaveRequest request) {
        if (request.getSobreNomeCorrentista() != null && request.getSobreNomeCorrentista().length() > 45) {
            throw new ResourceNotFoundException("Sobrenome do correntista muito longo");
        }
    }
}

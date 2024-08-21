package com.example.demo.core.repository.domain.validator.alterarpix;

import com.example.demo.core.repository.domain.request.PixChaveRequest;
import com.example.demo.core.repository.exceptions.ResourceNotFoundException;

public class SobreNomeCorrentistaValidationStrategy implements ValidationStrategy {
    @Override
    public void validate(PixChaveRequest request) {
        if (request.getSobreNomeCorrentista() != null && request.getSobreNomeCorrentista().length() > 45) {
            throw new ResourceNotFoundException("Sobrenome do correntista muito longo");
        }
    }
}

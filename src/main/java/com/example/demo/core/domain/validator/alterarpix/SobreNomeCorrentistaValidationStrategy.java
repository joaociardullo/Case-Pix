package com.example.demo.core.domain.validator.alterarpix;

import com.example.demo.core.domain.request.PixChaveRequestDTO;
import com.example.demo.core.exceptions.ResourceNotFoundException;

public class SobreNomeCorrentistaValidationStrategy implements ValidationStrategy {
    @Override
    public void validate(PixChaveRequestDTO request) {
        if (request.getSobreNomeCorrentista() != null && request.getSobreNomeCorrentista().length() > 45) {
            throw new ResourceNotFoundException("Sobrenome do correntista muito longo");
        }
    }
}

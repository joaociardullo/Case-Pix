package com.example.demo.core.domain.validator.alterarpix;

import com.example.demo.core.domain.request.PixChaveRequestDTO;
import com.example.demo.core.exceptions.ResourceNotFoundException;

public class NomeCorrentistaValidationStrategy implements ValidationStrategy {
    @Override
    public void validate(PixChaveRequestDTO request) {
        if (request.getNomeCorrentista().length() > 30) {
            throw new ResourceNotFoundException("");
        }
    }
}

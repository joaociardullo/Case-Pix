package com.example.demo.core.repository.domain.validator.alterarpix;

import com.example.demo.core.repository.domain.request.PixChaveRequest;
import com.example.demo.core.repository.exceptions.ResourceNotFoundException;

public class NomeCorrentistaValidationStrategy implements ValidationStrategy {
    @Override
    public void validate(PixChaveRequest request) {
        if (request.getNomeCorrentista().length() > 30) {
            throw new ResourceNotFoundException("");
        }
    }
}

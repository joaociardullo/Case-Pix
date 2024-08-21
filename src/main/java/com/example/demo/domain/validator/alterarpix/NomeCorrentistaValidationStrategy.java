package com.example.demo.domain.validator.alterarpix;

import com.example.demo.domain.request.PixChaveRequest;
import com.example.demo.exceptions.ResourceNotFoundException;

public class NomeCorrentistaValidationStrategy implements ValidationStrategy {
    @Override
    public void validate(PixChaveRequest request) {
        if (request.getNomeCorrentista().length() > 30) {
            throw new ResourceNotFoundException("");
        }
    }
}

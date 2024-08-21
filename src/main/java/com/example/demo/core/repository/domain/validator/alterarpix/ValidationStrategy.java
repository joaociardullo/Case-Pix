package com.example.demo.core.repository.domain.validator.alterarpix;

import com.example.demo.core.repository.domain.request.PixChaveRequest;

public interface ValidationStrategy {
    void validate(PixChaveRequest request);
}

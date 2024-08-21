package com.example.demo.domain.validator.alterarpix;

import com.example.demo.domain.request.PixChaveRequest;

public interface ValidationStrategy {
    void validate(PixChaveRequest request);
}

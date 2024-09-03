package com.example.demo.core.domain.validator.alterarpix;

import com.example.demo.core.domain.request.PixChaveRequestDTO;

public interface ValidationStrategy {
    void validate(PixChaveRequestDTO request);
}

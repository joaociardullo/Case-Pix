package com.example.demo.core.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDTO {
    private String status;
    private String message;
    private String erro;
}

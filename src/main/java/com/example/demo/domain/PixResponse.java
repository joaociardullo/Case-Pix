package com.example.demo.domain;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class PixResponse {

    private static HttpStatus status;

    private String id;

}

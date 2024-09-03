package com.example.demo.core.domain.response;

import com.example.demo.core.domain.entity.PixChave;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

@Getter
@Setter
@Builder
public class ResponseDTO {

    private HttpStatus erro;
    private String message;
    private ArrayList<PixChave> content;

}

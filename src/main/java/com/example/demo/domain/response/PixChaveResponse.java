package com.example.demo.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PixChaveResponse {

    private UUID id;

    @NotNull
    private String tipoChave;
    @NotNull
    private String valorChave;
    @NotNull
    private String tipoConta;
    @NotNull
    private Integer numeroAgencia;
    @NotNull
    private Integer numeroConta;
    @NotNull
    private String nomeCorrentista;

    private String sobreNomeCorrentista;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataRegistro;
}

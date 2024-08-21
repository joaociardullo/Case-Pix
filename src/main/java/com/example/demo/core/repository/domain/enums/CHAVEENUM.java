package com.example.demo.core.repository.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CHAVEENUM {
    INATIVA("INATIVIDADE"),

    ATIVA("ativa");

    private final String descricao;

}

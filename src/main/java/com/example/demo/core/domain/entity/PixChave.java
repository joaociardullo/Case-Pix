package com.example.demo.core.domain.entity;

import com.example.demo.core.domain.enums.CHAVEENUM;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "CHAVEPIX")
public class PixChave {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
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

    @Column(name = "dataRegistro")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataRegistro;

    @Column(name = "dataRegistro", insertable = false, updatable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private String dataInclusao;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "dataInativacao", insertable = false, updatable = false)
    private String dataInativacao;

    public Boolean isInativa() {
        if (tipoChave == CHAVEENUM.INATIVA.getDescricao()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}

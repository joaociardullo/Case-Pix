package com.example.demo.domain.entity;

import com.example.demo.domain.enums.CHAVEENUM;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.GenericGenerator;

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
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
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

    private LocalDateTime dataRegistro;

    public Boolean isInativa() {
        if (tipoChave == CHAVEENUM.INATIVA.getDescricao()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}

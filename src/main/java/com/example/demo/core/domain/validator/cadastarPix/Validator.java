package com.example.demo.core.domain.validator.cadastarPix;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Validator {

    public TipoChaveValidator getValidator(String tipoChave) {
        switch (tipoChave) {
            case "celular":
                return new CelularValidator();
            case "email":
                return new EmailValidator();
            case "cpf":
                return new CpfValidator();
            default:
                throw new IllegalArgumentException("Tipo de chave não suportado");
        }
    }
}

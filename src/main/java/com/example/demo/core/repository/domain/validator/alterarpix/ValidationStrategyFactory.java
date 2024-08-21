package com.example.demo.core.repository.domain.validator.alterarpix;

import com.example.demo.core.repository.domain.request.PixChaveRequest;
import com.example.demo.core.repository.exceptions.ResourceNotFoundException;

public class ValidationStrategyFactory {
    public static ValidationStrategy getValidationStrategy(PixChaveRequest request) {
        if (request.getTipoConta() != null) {
            return new TipoContaValidationStrategy();
        } else if (request.getNumeroAgencia() != null) {
            return new NumeroAgenciaValidationStrategy();
        } else if (request.getNumeroConta() != null) {
            return new NumeroContaValidationStrategy();
        } else if (request.getNomeCorrentista() != null) {
            return new NomeCorrentistaValidationStrategy();
        } else if (request.getSobreNomeCorrentista() != null) {
            return new SobreNomeCorrentistaValidationStrategy();
        } else {
            throw new ResourceNotFoundException("Campos obrigatórios não preenchidos");
        }
    }
}

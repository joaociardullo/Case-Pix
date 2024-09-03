package com.example.demo.outbound.service.impl;

import com.example.demo.core.domain.entity.PixChave;
import com.example.demo.core.domain.request.PixChaveRequestDTO;
import com.example.demo.core.domain.response.PixChaveResponse;
import com.example.demo.core.domain.response.ResponseDTO;
import com.example.demo.core.domain.validator.alterarpix.ValidationStrategy;
import com.example.demo.core.domain.validator.alterarpix.ValidationStrategyFactory;
import com.example.demo.core.domain.validator.cadastarPix.TipoChaveValidator;
import com.example.demo.core.domain.validator.cadastarPix.Validator;
import com.example.demo.core.exceptions.IllegalArgumentException;
import com.example.demo.core.exceptions.ResourceNotFoundException;
import com.example.demo.core.mapper.PixMapper;
import com.example.demo.core.repository.PixChaveRepository;
import com.example.demo.outbound.service.PixService;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.valueOf;

@Service
public class PixServiceImpl implements PixService {

    private static final Logger log = LoggerFactory.getLogger(PixServiceImpl.class);
    private final PixChaveRepository repository;
    private final Validator validator;
    private final PixMapper mapper;

    public PixServiceImpl(PixChaveRepository repository, Validator validator, PixMapper mapper) {
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    public PixChaveResponse criarChavePix(PixChaveRequestDTO request) throws Exception {

        try {
            if (request.getTipoChave() == null || request.getValorChave() == null || request.getTipoConta() == null
                    || request.getNumeroAgencia() == null || request.getNumeroConta() == null || request.getNomeCorrentista() == null) {
                throw new IllegalArgumentException("Campos obrigatorios");
            }

            TipoChaveValidator tipoChaveValidator = validator.getValidator(request.getTipoChave());
            tipoChaveValidator.validate(request.getValorChave());

            var chaveExistente = repository.findByValorChave(request.getValorChave());
            if (chaveExistente.isPresent()) {
                throw new InvalidDataAccessResourceUsageException("Chave já existente");
            }

            var conta = repository.findByNumeroConta(request.getNumeroConta());
            if (conta.isPresent() && conta.get().getValorChave().length() >= 5) {
                throw new ResourceNotFoundException("Limite de chaves por conta excedido");
            }

            PixChave pixChave = mapper.toEntity(request);
            log.info("Dados da chave cadastrado: {} ", pixChave);
            repository.save(pixChave);

            PixChaveResponse response = getPixChaveResponse(pixChave);

            return response;

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private static PixChaveResponse getPixChaveResponse(PixChave pixChave) {
        PixChaveResponse response = new PixChaveResponse();
        response.setId(pixChave.getId());
        response.setTipoChave(pixChave.getTipoChave());
        response.setValorChave(pixChave.getValorChave());
        response.setTipoConta(pixChave.getTipoConta());
        response.setNumeroAgencia(pixChave.getNumeroAgencia());
        response.setNumeroConta(pixChave.getNumeroConta());
        response.setNomeCorrentista(pixChave.getNomeCorrentista());
        response.setSobreNomeCorrentista(pixChave.getSobreNomeCorrentista());
        response.setDataRegistro(LocalDateTime.now());
        return response;
    }

    @Override
    public Optional<PixChave> alterarChavePix(@PathParam("id") UUID id, PixChaveRequestDTO request) {
        try {

            ValidationStrategy validationStrategy = ValidationStrategyFactory.getValidationStrategy(request);
            validationStrategy.validate(request);

            Optional<PixChave> chave = repository.findById(id);
            if (chave.isEmpty()) {
                throw new IllegalArgumentException("Chave não encontrada");
            }

            if (chave.get().isInativa()) {
                chave.get().setDataInativacao(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                throw new IllegalArgumentException("Chave inativa");
            }

            chave.get().setTipoConta(request.getTipoConta());
            chave.get().setNumeroAgencia(request.getNumeroAgencia());
            chave.get().setNumeroConta(request.getNumeroConta());
            chave.get().setNomeCorrentista(request.getNomeCorrentista());
            chave.get().setSobreNomeCorrentista(request.getSobreNomeCorrentista());
            log.info("PIX ATUALIZADO: {}", chave);
            return Optional.of(repository.save(chave.get()));

        } catch (Exception ex) {
            throw new InvalidDataAccessResourceUsageException(ex.getMessage());
        }
    }


    @Override
    public List<PixChave> consultarChavesPix(UUID id, String tipoChave, String nomeCorrentista, String dataInclusao, String dataInativacao) {
        try {
            if (id != null) {
                if (tipoChave != null || nomeCorrentista != null || dataInclusao != null || dataInativacao != null) {
                    throw new IllegalArgumentException("Não é permitido combinar filtros com ID");
                }
                Optional<PixChave> chave = repository.findById(id);
                if (!chave.isPresent()) {
                    throw new IllegalArgumentException("Chave não encontrada");
                }
                return Collections.singletonList(chave.get());
            }

            if (dataInclusao != null && dataInativacao != null) {
                throw new ResourceNotFoundException("Não é permitido combinar data de inclusão e inativação");
            }

            List<PixChave> chaves = repository.findByFilters(tipoChave, nomeCorrentista, dataInclusao, dataInativacao);

            ArrayList<PixChave> chavesArrayList = new ArrayList<>(chaves);

            if (chaves.isEmpty()) {
                return ResponseDTO.builder()
                        .erro(valueOf(BAD_REQUEST.value()))
                        .message("Nenhuma chave encontrada")
                        .content(null)
                        .build().getContent();
            }

            return chavesArrayList;

        } catch (Exception e) {
            throw new InvalidDataAccessResourceUsageException(e.fillInStackTrace().getMessage());

        }

    }

}
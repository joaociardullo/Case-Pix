package com.example.demo.service.impl;

import com.example.demo.domain.ResponseDTO;
import com.example.demo.domain.entity.PixChave;
import com.example.demo.domain.request.PixChaveRequest;
import com.example.demo.domain.response.PixChaveResponse;
import com.example.demo.exceptions.IllegalArgumentException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repository.PixChaveRepository;
import com.example.demo.service.PixService;
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

    public PixServiceImpl(PixChaveRepository repository) {
        this.repository = repository;
    }

    @Override
    public PixChaveResponse criarChavePix(PixChaveRequest request) throws Exception {

        try {
            if (request.getTipoChave() == null || request.getValorChave() == null || request.getTipoConta() == null
                    || request.getNumeroAgencia() == null || request.getNumeroConta() == null || request.getNomeCorrentista() == null) {
                throw new IllegalArgumentException("Campos obrigatorios");
            }

            if (!Arrays.asList("celular", "email", "cpf", "cnpj", "aleatorio").contains(request.getTipoChave())) {
                throw new IllegalArgumentException("Tipos de Chaves");
            }
            if (request.getTipoChave().equals("celular")) {
                if (!request.getValorChave().startsWith("+") || request.getValorChave().length() != 14) {
                    throw new ResourceNotFoundException("Celular nao segue o padrao");
                }
            } else if (request.getTipoChave().equals("email")) {
                if (!request.getValorChave().contains("@") || request.getValorChave().length() > 77) {
                    throw new ResourceNotFoundException("email não segue o padrao");
                }
            } else if (request.getTipoChave().equals("cpf")) {
                if (request.getValorChave().length() != 11 || !request.getValorChave().matches("\\d+")) {
                    throw new ResourceNotFoundException("CPF não segue o padrao");
                }
            }
            var chaveExistente = repository.findByValorChave(request.getValorChave());
            if (chaveExistente.isPresent()) {
                throw new InvalidDataAccessResourceUsageException("Chave já existente");
            }

            var conta = repository.findByNumeroConta(request.getNumeroConta());
            if (conta.isPresent() && conta.get().getValorChave().length() > 5) {
                throw new ResourceNotFoundException("Limite de chaves por conta excedido,pessoa fisica");
            }

            PixChave pixChave = new PixChave();
            pixChave.setTipoChave(request.getTipoChave());
            pixChave.setValorChave(request.getValorChave());
            pixChave.setTipoConta(request.getTipoConta());
            pixChave.setNumeroAgencia(request.getNumeroAgencia());
            pixChave.setNumeroConta(request.getNumeroConta());
            pixChave.setNomeCorrentista(request.getNomeCorrentista());
            pixChave.setSobreNomeCorrentista(request.getSobreNomeCorrentista());
            pixChave.setDataRegistro(LocalDateTime.now());
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
        response.setDataRegistro(pixChave.getDataRegistro());
        return response;
    }

    @Override
    public Optional<PixChave> alterarChavePix(@PathParam("id") UUID id, PixChaveRequest request) {
        try {
            if (request.getTipoConta() == null || request.getNumeroAgencia() == null || request.getNumeroConta() == null
                    || request.getNomeCorrentista() == null) {
                throw new ResourceNotFoundException("Campos obrigatórios não preenchidos");
            }

            if (!Arrays.asList("corrente", "poupança").contains(request.getTipoConta())) {
                throw new ResourceNotFoundException("Tipo de conta inválido");
            }

            String numeroAgencia = Integer.toString(request.getNumeroAgencia());
            if (!(numeroAgencia.matches("\\d{4}"))) {
                throw new ResourceNotFoundException("Tipo de agencia invalida");
            }

            String numeroConta = Integer.toString(request.getNumeroConta());
            if (!numeroConta.matches("\\d{8}")) {
                throw new ResourceNotFoundException("Tipo de numero conta invalida");
            }

            if (request.getNomeCorrentista().length() > 30) {
                throw new ResourceNotFoundException("");
            }

            if (request.getSobreNomeCorrentista() != null && request.getSobreNomeCorrentista().length() > 45) {
                throw new ResourceNotFoundException("Sobrenome do correntista muito longo");
            }

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
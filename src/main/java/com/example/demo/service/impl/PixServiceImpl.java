package com.example.demo.service.impl;

import com.example.demo.domain.entity.PixChave;
import com.example.demo.domain.request.PixChaveRequest;
import com.example.demo.domain.response.PixChaveResponse;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repository.PixChaveRepository;
import com.example.demo.service.PixService;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
public class PixServiceImpl implements PixService {

    private static final Logger log = LoggerFactory.getLogger(PixServiceImpl.class);
    private final PixChaveRepository repository;

    public PixServiceImpl(PixChaveRepository repository) {
        this.repository = repository;
    }

    public PixChaveResponse criarChavePix(PixChaveRequest request) throws Exception {

        try {
            // Validar campos obrigatórios
            if (request.getTipoChave() == null || request.getValorChave() == null || request.getTipoConta() == null
                    || request.getNumeroAgencia() == null || request.getNumeroConta() == null || request.getNomeCorrentista() == null) {
                throw new IllegalArgumentException("Campos obrigatorios");
            }

            // Validar tipo de chave
            if (!Arrays.asList("celular", "email", "cpf", "cnpj", "aleatorio").contains(request.getTipoChave())) {
                throw new IllegalArgumentException("Tipos de Chaves");
            }
            // Validar valor da chave
            if (request.getTipoChave().equals("celular")) {
                // Validar código país, DDD e número
                if (!request.getValorChave().startsWith("+") || request.getValorChave().length() != 13) {
                    throw new ResourceNotFoundException("Celular nao segue o padrao");
                }
            } else if (request.getTipoChave().equals("email")) {
                // Validar formato de e-mail
                if (!request.getValorChave().contains("@") || request.getValorChave().length() > 77) {
                    throw new ResourceNotFoundException("email não segue o padrao");
                }
            } else if (request.getTipoChave().equals("cpf")) {
                // Validar formato de CPF
                if (request.getValorChave().length() != 11 || !request.getValorChave().matches("\\d+")) {
                    throw new ResourceNotFoundException("CPF não segue o padrao");
                }
            }
            log.info("PASSSSEI");
            // Verificar se a chave já existe
            var chaveExistente = repository.findByValorChave(request.getValorChave());
            if (chaveExistente.isPresent()) {
                throw new InvalidDataAccessResourceUsageException("Chave já existente");
            }

            // Verificar limite de chaves por conta
            var conta = repository.findByNumeroConta(request.getNumeroConta());
            if (conta.isPresent() && conta.get().getValorChave().length() > 5) {
                throw new ResourceNotFoundException("Limite de chaves por conta excedido,pessoa fisica");
            }

            // Criar nova chave
            PixChave pixChave = new PixChave();
            pixChave.setId(UUID.randomUUID());
            pixChave.setTipoChave(request.getTipoChave());
            pixChave.setValorChave(request.getValorChave());
            pixChave.setTipoConta(request.getTipoConta());
            pixChave.setNumeroAgencia(request.getNumeroAgencia());
            pixChave.setNumeroConta(request.getNumeroConta());
            pixChave.setNomeCorrentista(request.getNomeCorrentista());
            pixChave.setSobreNomeCorrentista(request.getSobreNomeCorrentista());
            pixChave.setDataRegistro(LocalDateTime.now());
            log.info("Dados da chave cadastrado: {} ", pixChave);
            // Salvar nova chave no banco de dados
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
            // Validar campos obrigatórios
            if (request.getTipoConta() == null || request.getNumeroAgencia() == null || request.getNumeroConta() == null
                    || request.getNomeCorrentista() == null) {
                throw new ResourceNotFoundException("Campos obrigatórios não preenchidos");
            }

            // Validar tipo de conta
            if (!Arrays.asList("corrente", "poupança").contains(request.getTipoConta())) {
                throw new ResourceNotFoundException("Tipo de conta inválido");
            }

            // Validar número de agência
            String numeroAgencia = Integer.toString(request.getNumeroAgencia());
            if (!(numeroAgencia.matches("\\d{4}"))) {
                throw new ResourceNotFoundException("Tipo de agencia invalida");
            }

            // Validar número de conta
            String numeroConta = Integer.toString(request.getNumeroConta());
            if (!numeroConta.matches("\\d{8}")) {
                throw new ResourceNotFoundException("Tipo de numero conta invalida");
            }

            // Validar nome do correntista
            if (request.getNomeCorrentista().length() > 30) {
                throw new ResourceNotFoundException("");
            }

            // Validar sobrenome do correntista
            if (request.getSobreNomeCorrentista() != null && request.getSobreNomeCorrentista().length() > 45) {
                throw new ResourceNotFoundException("Sobrenome do correntista muito longo");
            }

            // Buscar chave pelo ID
            Optional<PixChave> chave = repository.findById(id);
            if (chave.isEmpty()) {
                throw new IllegalArgumentException("Chave não encontrada");
            }

            // Verificar se a chave está inativa
            if (chave.get().isInativa()) {
                throw new IllegalArgumentException("Chave inativa");
            }

            // Alterar valores da chave
            chave.get().setTipoConta(request.getTipoConta());
            chave.get().setNumeroAgencia(request.getNumeroAgencia());
            chave.get().setNumeroConta(request.getNumeroConta());
            chave.get().setNomeCorrentista(request.getNomeCorrentista());
            chave.get().setSobreNomeCorrentista(request.getSobreNomeCorrentista());
            log.info("PIX ATUALIZADO: {}", chave);
            return Optional.of(repository.save(chave.get()));


//            PixChaveResponse response = new PixChaveResponse();
//            response.setTipoConta(chave.get().getTipoConta());
//            response.setNumeroAgencia(chave.get().getNumeroAgencia());
//            response.setNumeroConta(chave.get().getNumeroConta());
//            response.setNomeCorrentista(chave.get().getNomeCorrentista());
//            response.setSobreNomeCorrentista(chave.get().getSobreNomeCorrentista());
        } catch (Exception ex) {
            try {
                throw new Exception(ex.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
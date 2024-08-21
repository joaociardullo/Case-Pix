package com.example.demo.service;

import com.example.demo.domain.entity.PixChave;
import com.example.demo.domain.request.PixChaveRequest;
import com.example.demo.domain.response.PixChaveResponse;
import jakarta.websocket.server.PathParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PixService {

    PixChaveResponse criarChavePix(PixChaveRequest request) throws Exception;

    Optional<PixChave> alterarChavePix(@PathParam("id") UUID id, PixChaveRequest request);

    List<PixChave> consultarChavesPix(UUID id, String tipoChave, String nomeCorrentista, String dataInclusao, String dataInativacao);
}

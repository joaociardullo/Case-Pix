package com.example.demo.outbound.service;

import com.example.demo.core.domain.entity.PixChave;
import com.example.demo.core.domain.request.PixChaveRequestDTO;
import com.example.demo.core.domain.response.PixChaveResponse;
import jakarta.websocket.server.PathParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PixService {

    PixChaveResponse criarChavePix(PixChaveRequestDTO request) throws Exception;

    Optional<PixChave> alterarChavePix(@PathParam("id") UUID id, PixChaveRequestDTO request);

    List<PixChave> consultarChavesPix(UUID id, String tipoChave, String nomeCorrentista, String dataInclusao, String dataInativacao);
}

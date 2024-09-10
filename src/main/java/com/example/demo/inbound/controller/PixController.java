package com.example.demo.inbound.controller;

import com.example.demo.core.domain.entity.PixChave;
import com.example.demo.core.domain.request.PixChaveRequestDTO;
import com.example.demo.core.domain.response.PixChaveResponse;
import com.example.demo.outbound.service.impl.PixServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/casepix/v1")
@Slf4j
public class PixController {

    private final PixServiceImpl service;

    public PixController(PixServiceImpl service) {
        this.service = service;
    }

    @ApiOperation("Metodo para cadastrar api")
    @PostMapping(value = "/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PixChaveResponse> cadastrar(@RequestBody PixChaveRequestDTO request) throws Exception {
        log.info("CADASTRAR CHAVE PIX: [{}] ", request);
        var result = service.criarChavePix(request);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "Atualizar chave pix")
    @PutMapping(value = "/atualizar/{id}")
    public ResponseEntity<Optional<PixChave>> atulizarPix(@PathVariable UUID id, @RequestBody PixChaveRequestDTO request) throws Exception {
        log.info("ATUALIZAR CHAVE PIX: [{}] ", request);
        var result = service.alterarChavePix(id, request);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "Consultar chaves de api")
    @GetMapping(value = "/consultar")
    public ResponseEntity<List<PixChave>> consultarChaves(@RequestParam(value = "id", required = false) UUID id,
                                                          @RequestParam(value = "tipoChave", required = false) String tipoChave,
                                                          @RequestParam(value = "nomeCorrentista", required = false) String nomeCorrentista,
                                                          @RequestParam(value = "dataInclusao", required = false) String dataInclusao,
                                                          @RequestParam(value = "dataInativacao", required = false) String dataInativacao) {
        log.info("CONSULTAR CHAVE PIX: {}", id, tipoChave, nomeCorrentista, dataInclusao, dataInativacao);
        var result = service.consultarChavesPix(id, tipoChave, nomeCorrentista, dataInclusao, dataInativacao);
        return ResponseEntity.ok(result);
    }


    @ApiOperation(value = "Deletar pix", code = 200)
    @DeleteMapping(value = "/deletar/{id}")
    public ResponseEntity<?> deletarChavePix(@PathVariable("id") UUID id) {
        service.deletarPix(id);
        log.info("Chave Deletada: {}", id);
        return ResponseEntity.ok().build();
    }
}

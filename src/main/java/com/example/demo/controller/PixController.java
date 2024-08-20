package com.example.demo.controller;

import com.example.demo.domain.entity.PixChave;
import com.example.demo.domain.request.PixChaveRequest;
import com.example.demo.domain.response.PixChaveResponse;
import com.example.demo.service.impl.PixServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/cadastrar")
    public PixChaveResponse cadastrar(@RequestBody PixChaveRequest request) throws Exception {
        log.info("CADASTRAR CHAVE PIX: [{}] ", request);
        return service.criarChavePix(request);
    }

    @PutMapping(value = "/atualizar/{id}")
    public Optional<PixChave> atulizarPix(@PathVariable UUID id, @RequestBody PixChaveRequest request) throws Exception {
        log.info("ATUALIZAR CHAVE PIX: [{}] ", request);
        return service.alterarChavePix(id, request);
    }
}

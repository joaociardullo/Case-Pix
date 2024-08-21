package com.example.demo.inbound.controller;

import com.example.demo.core.repository.domain.entity.PixChave;
import com.example.demo.core.repository.domain.request.PixChaveRequest;
import com.example.demo.core.repository.domain.response.PixChaveResponse;
import com.example.demo.outbound.service.impl.PixServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PixControllerTest {

    @InjectMocks
    private PixController controller;

    @Mock
    PixServiceImpl service;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_create_pix_key_success() throws Exception {
        PixChaveRequest request = new PixChaveRequest();
        request.setTipoChave("CPF");
        request.setValorChave("12345678900");
        request.setTipoConta("CONTA_CORRENTE");
        request.setNumeroAgencia(1234);
        request.setNumeroConta(56789);
        request.setNomeCorrentista("John Doe");

        PixChaveResponse expectedResponse = new PixChaveResponse();
        expectedResponse.setId(UUID.randomUUID());
        expectedResponse.setTipoChave("CPF");
        expectedResponse.setValorChave("12345678900");
        expectedResponse.setTipoConta("CONTA_CORRENTE");
        expectedResponse.setNumeroAgencia(1234);
        expectedResponse.setNumeroConta(56789);
        expectedResponse.setNomeCorrentista("John Doe");

        PixServiceImpl service = mock(PixServiceImpl.class);
        when(service.criarChavePix(request)).thenReturn(expectedResponse);

        PixController controller = new PixController(service);

        PixChaveResponse actualResponse = controller.cadastrar(request);

        assertEquals(expectedResponse, actualResponse);
    }


    @Test
    void test_update_pix_key_success() throws Exception {
        UUID id = UUID.randomUUID();
        PixChaveRequest request = new PixChaveRequest();
        request.setTipoChave("EMAIL");
        request.setValorChave("john.doe@example.com");
        request.setTipoConta("CONTA_POUPANCA");
        request.setNumeroAgencia(4321);
        request.setNumeroConta(98765);
        request.setNomeCorrentista("John Doe");

        PixChave pixChave = new PixChave();
        pixChave.setId(id);
        pixChave.setTipoChave("EMAIL");
        pixChave.setValorChave("john.doe@example.com");
        pixChave.setTipoConta("CONTA_POUPANCA");
        pixChave.setNumeroAgencia(4321);
        pixChave.setNumeroConta(98765);
        pixChave.setNomeCorrentista("John Doe");

        PixServiceImpl service = mock(PixServiceImpl.class);
        when(service.alterarChavePix(id, request)).thenReturn(Optional.of(pixChave));

        PixController controller = new PixController(service);

        Optional<PixChave> actualResponse = controller.atulizarPix(id, request);

        assertTrue(actualResponse.isPresent());
        assertEquals(pixChave, actualResponse.get());
    }

    @Test
    void test_retrieve_pix_keys_with_filters() {
        UUID id = null;
        String tipoChave = "EMAIL";
        String nomeCorrentista = "John Doe";
        String dataInclusao = null;
        String dataInativacao = null;

        List<PixChave> expectedKeys = new ArrayList<>();
        PixChave pixChave = new PixChave();
        pixChave.setId(UUID.randomUUID());
        pixChave.setTipoChave("EMAIL");
        pixChave.setValorChave("john.doe@example.com");
        pixChave.setTipoConta("CONTA_POUPANCA");
        pixChave.setNumeroAgencia(4321);
        pixChave.setNumeroConta(98765);
        pixChave.setNomeCorrentista("John Doe");
        expectedKeys.add(pixChave);

        PixServiceImpl service = mock(PixServiceImpl.class);
        when(service.consultarChavesPix(id, tipoChave, nomeCorrentista, dataInclusao, dataInativacao)).thenReturn(expectedKeys);

        PixController controller = new PixController(service);

        List<PixChave> actualKeys = controller.consultarChaves(id, tipoChave, nomeCorrentista, dataInclusao, dataInativacao);

        assertEquals(expectedKeys, actualKeys);
    }

    @Test
    void test_update_pix_key_non_existent_id() {
        UUID id = UUID.randomUUID();

        PixServiceImpl service = mock(PixServiceImpl.class);

        when(service.alterarChavePix(id, new PixChaveRequest())).thenThrow(new IllegalArgumentException("Chave nￃﾣo encontrada"));

        PixController controller = new PixController(service);

        assertThrows(IllegalArgumentException.class, () -> {
            controller.atulizarPix(id, new PixChaveRequest());
        });
    }
}
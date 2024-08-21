package com.example.demo.outbound.service.impl;

import com.example.demo.core.repository.PixChaveRepository;
import com.example.demo.core.repository.domain.entity.PixChave;
import com.example.demo.core.repository.domain.request.PixChaveRequest;
import com.example.demo.core.repository.domain.response.PixChaveResponse;
import com.example.demo.core.repository.domain.validator.alterarpix.ValidationStrategy;
import com.example.demo.core.repository.domain.validator.alterarpix.ValidationStrategyFactory;
import com.example.demo.core.repository.domain.validator.cadastarPix.TipoChaveValidator;
import com.example.demo.core.repository.domain.validator.cadastarPix.Validator;
import com.example.demo.core.repository.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PixServiceImplTest {

    @InjectMocks
    PixServiceImpl service;
    @Mock
    PixChaveRepository repository;
    @Mock
    Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_successfully_creates_pixchave() throws Exception {
        // Given
        PixChaveRequest request = new PixChaveRequest();
        request.setTipoChave("email");
        request.setValorChave("test@example.com");
        request.setTipoConta("corrente");
        request.setNumeroAgencia(1234);
        request.setNumeroConta(56789);
        request.setNomeCorrentista("John Doe");
        request.setSobreNomeCorrentista("Doe");

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

        PixChaveResponse expectedResponse = new PixChaveResponse();
        expectedResponse.setId(pixChave.getId());
        expectedResponse.setTipoChave(pixChave.getTipoChave());
        expectedResponse.setValorChave(pixChave.getValorChave());
        expectedResponse.setTipoConta(pixChave.getTipoConta());
        expectedResponse.setNumeroAgencia(pixChave.getNumeroAgencia());
        expectedResponse.setNumeroConta(pixChave.getNumeroConta());
        expectedResponse.setNomeCorrentista(pixChave.getNomeCorrentista());
        expectedResponse.setSobreNomeCorrentista(pixChave.getSobreNomeCorrentista());
        expectedResponse.setDataRegistro(pixChave.getDataRegistro());

        PixServiceImpl service = new PixServiceImpl(repository, validator);

        when(repository.findByValorChave(anyString())).thenReturn(Optional.empty());
        when(repository.findByNumeroConta(anyInt())).thenReturn(Optional.empty());
        when(repository.save(any(PixChave.class))).thenReturn(pixChave);

        PixChaveResponse response = service.criarChavePix(request);

        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void testValidatesTipochave() throws Exception {
        PixChaveRequest request = new PixChaveRequest();
        request.setTipoChave("email");
        request.setValorChave("test@example.com");
        request.setTipoConta("corrente");
        request.setNumeroAgencia(1234);
        request.setNumeroConta(56789);
        request.setNomeCorrentista("John Doe");

        TipoChaveValidator tipoChaveValidator = mock(TipoChaveValidator.class);
        Validator validator = mock(Validator.class);

        when(validator.getValidator(anyString())).thenReturn(tipoChaveValidator);

        PixServiceImpl service = new PixServiceImpl(repository, validator);

        service.criarChavePix(request);

        verify(tipoChaveValidator, times(1)).validate(request.getValorChave());
    }

    @Test
    void test_throws_illegalargumentexception_for_null_fields() {
        PixServiceImpl service = new PixServiceImpl(repository, validator);

        PixChaveRequest request = new PixChaveRequest();

        assertThrows(IllegalArgumentException.class, () -> {
            service.criarChavePix(request);
        });
    }

    @Test
    void test_throws_invaliddataaccessresourceusageexception_for_existing_chave() {
        PixServiceImpl service = new PixServiceImpl(repository, validator);

        PixChaveRequest request = new PixChaveRequest();
        request.setTipoChave("email");
        request.setValorChave("test@example.com");

        when(repository.findByValorChave(anyString())).thenReturn(Optional.of(new PixChave()));

        assertThrows(InvalidDataAccessResourceUsageException.class, () -> {
            service.criarChavePix(request);
        });
    }

    @Test
    void validate_request_using_appropriate_validation_strategy() {
        // Given
        UUID id = UUID.randomUUID();
        PixChaveRequest request = new PixChaveRequest();
        request.setTipoConta("CONTA_CORRENTE");
        request.setNumeroAgencia(1234);
        request.setNumeroConta(56789);
        request.setNomeCorrentista("John");
        request.setSobreNomeCorrentista("Doe");

        ValidationStrategyFactory factory = mock(ValidationStrategyFactory.class);
        ValidationStrategy strategy = mock(ValidationStrategy.class);
        when(factory.getValidationStrategy(request)).thenReturn(strategy);

        PixServiceImpl service = new PixServiceImpl(repository, validator);

        // When
        service.alterarChavePix(id, request);

        // Then
        verify(strategy).validate(request);
    }

    @Test
    void find_pixchave_by_id_in_repository() {
        UUID id = UUID.randomUUID();
        PixChaveRequest request = new PixChaveRequest();
        request.setTipoConta("CONTA_CORRENTE");
        request.setNumeroAgencia(1227);
        request.setNumeroConta(34477);
        request.setNomeCorrentista("John");
        request.setSobreNomeCorrentista("Doe");

        PixChave pixChave = new PixChave();
        pixChave.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(pixChave));

        PixServiceImpl service = new PixServiceImpl(repository, validator);

        Optional<PixChave> result = service.alterarChavePix(id, request);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    void request_with_null_id() {
        UUID id = null;
        PixChaveRequest request = new PixChaveRequest();

        PixServiceImpl service = new PixServiceImpl(repository, validator);

        assertThrows(InvalidDataAccessResourceUsageException.class, () -> {
            service.alterarChavePix(id, request);
        });
    }


    @Test
     void test_retrieve_pixchave_by_id() {
        UUID id = UUID.randomUUID();
        PixChave pixChave = new PixChave();
        pixChave.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(pixChave));

        List<PixChave> result = service.consultarChavesPix(id, null, null, null, null);

        assertEquals(1, result.size());
        assertEquals(id, result.get(0).getId());
    }

    @Test
     void test_retrieve_pixchave_by_filters() {
        String tipoChave = "CPF";
        String nomeCorrentista = "John Doe";
        List<PixChave> pixChaves = Arrays.asList(new PixChave(), new PixChave());
        when(repository.findByFilters(tipoChave, nomeCorrentista, null, null)).thenReturn(pixChaves);

        List<PixChave> result = service.consultarChavesPix(null, tipoChave, nomeCorrentista, null, null);

        assertEquals(2, result.size());
    }

    @Test
     void test_throw_resourcenotfoundexception_combined_data_inclusao_inativacao() {

        assertThrows(ResourceNotFoundException.class, () -> {
            service.consultarChavesPix(null, null, null, "2023-01-01", "2023-01-02");
        });
    }

    @Test
     void test_throw_illegalargumentexception_combined_filters_with_id() {
        UUID id = UUID.randomUUID();

        assertThrows(InvalidDataAccessResourceUsageException.class, () -> {
            service.consultarChavesPix(id, "CPF", "John Doe", null, null);
        });
    }
}
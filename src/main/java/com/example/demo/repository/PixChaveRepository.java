package com.example.demo.repository;

import com.example.demo.domain.entity.PixChave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PixChaveRepository extends JpaRepository<PixChave, UUID> {

    Optional<PixChave> findByValorChave(String valorChave);

    Optional<PixChave> findByNumeroConta(Integer numeroConta);

    @Query("SELECT pc FROM PixChave pc "
            + "WHERE (:tipoChave IS NULL OR pc.tipoChave = :tipoChave) "
            + "AND (:nomeCorrentista IS NULL OR pc.nomeCorrentista = :nomeCorrentista) "
            + "AND (:dataInclusao IS NULL OR pc.dataInclusao = :dataInclusao) "
            + "AND (:dataInativacao IS NULL OR pc.dataInativacao = :dataInativacao)")
    List<PixChave> findByFilters(@Param("tipoChave") String tipoChave,
                                 @Param("nomeCorrentista") String nomeCorrentista,
                                 @Param("dataInclusao") String dataInclusao,
                                 @Param("dataInativacao") String dataInativacao);
}

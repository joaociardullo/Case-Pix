package com.example.demo.repository;

import com.example.demo.domain.entity.PixChave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PixChaveRepository extends JpaRepository<PixChave, UUID> {

    Optional<PixChave> findByValorChave(String valorChave);

    Optional<PixChave> findByNumeroConta(Integer numeroConta);

    Optional<PixChave> findById(UUID id);

}

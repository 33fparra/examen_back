package com.pe.EcoPunto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pe.EcoPunto.entity.usuarios;

@Repository
public interface usuariosRepository extends JpaRepository<usuarios, Long> {

    public abstract boolean existsByCorreoElectronico(String CorreoElectronico);
 Optional<usuarios> findByCorreoElectronico(String CorreoElectronico);
}

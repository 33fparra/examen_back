package com.pe.EcoPunto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pe.EcoPunto.entity.roles;

@Repository
public interface RolesRepository extends JpaRepository<roles, Long> {
    Optional<roles> findByNombre(String nombre);

}

package com.pe.EcoPunto.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
@Data
@Entity
@Table(name = "roles")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class roles{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    public roles(String nombre) {
        this.nombre = nombre;
    }

    public roles() {

    }

}

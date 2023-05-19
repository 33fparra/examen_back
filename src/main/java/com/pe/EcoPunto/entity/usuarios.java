package com.pe.EcoPunto.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@JsonIgnoreProperties({ "puntosReciclaje", "puntoMateriales" })
@Table(name = "usuarios")
public class usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "correo_electronico", nullable = false, unique = true)
    private String correoElectronico;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "direccion", nullable = false)
    private String direccion;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roles_id", nullable = false)
    private roles rol;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<PuntoReciclaje> puntosReciclaje;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<PuntoMaterial> puntoMateriales;

    public usuarios(String nombre, String correoElectronico, String contrasena,String telefono,String direccion, roles rol) {
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.direccion = direccion;
        this.rol = rol;
    }

}

package com.pe.EcoPunto.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "punto_reciclaje")
public class PuntoReciclaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    private String horarioAtencion;

    private Double latitud;

    private Double longitud;

    private String telefono;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarios_id", nullable = false)
    private usuarios usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "puntoReciclaje")
    private List<materiales_reciclables> materialesReciclables;
}

package com.pe.EcoPunto.entity;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Data
@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name = "materiales_reciclables")
public class materiales_reciclables {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long materialesReciclablesId;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "imagen", nullable = false)
    private String imagen;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puntoReciclajeId", nullable = false)
    private PuntoMaterial puntoMaterial;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private PuntoReciclaje puntoReciclaje;

    @JsonIgnore
    @OneToMany(mappedBy = "materialesReciclable")
    private List<ActividadReciclaje> actividadReciclajeList;


}

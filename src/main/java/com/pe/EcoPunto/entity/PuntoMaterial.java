package com.pe.EcoPunto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name = "punto_material")
public class PuntoMaterial
{
    @Id
    private Long puntoReciclajeId;

    private String nombrePuntoReciclaje;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private usuarios usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "puntoMaterial")
    private List<materiales_reciclables> materialesReciclables;
}

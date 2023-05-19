package com.pe.EcoPunto.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name = "actividad_reciclaje")
public class ActividadReciclaje
{
    @Id
    private Long actividadReciclajeId;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private int cantidad;

    private int tipoMaterial;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materialesReciclablesId", nullable = false)
    private materiales_reciclables materialesReciclable;
}

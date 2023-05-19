package com.pe.EcoPunto.controller;

import com.pe.EcoPunto.entity.PuntoMaterial;
import com.pe.EcoPunto.repository.PuntoMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/rest/v1/puntoMaterial")
public class PuntoMaterialController
{
    @Autowired
    private PuntoMaterialRepository puntoMaterialRepository;

    @GetMapping("listar")
    public ResponseEntity<?> listarPuntosMateriales()
    {
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(puntoMaterialRepository.findAll());
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error, por favor intente más tarde");
        }
    }

    @GetMapping("listar/{id}")
    public ResponseEntity<?> listarPuntoMaterial(@PathVariable("id") long id)
    {
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(puntoMaterialRepository.findById(id).orElse(null));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error, por favor intente más tarde");
        }
    }

    @PostMapping("guardar")
    public ResponseEntity<?> guardarPuntoMaterial(@RequestBody PuntoMaterial puntoMaterial)
    {
        try
        {
            if (puntoMaterial == null || (puntoMaterial.getPuntoReciclajeId() != null && puntoMaterialRepository.existsById(puntoMaterial.getPuntoReciclajeId())))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se puede guardar un Punto Material existente o no válido!");
            }

            puntoMaterialRepository.save(puntoMaterial);
            return ResponseEntity.status(HttpStatus.OK).body("Se guardo correctamente!");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error al guardar!");
        }
    }

    @PutMapping("actualizar/{id}")
    public ResponseEntity<?> actualizarMaterial(@RequestBody PuntoMaterial puntoMaterial, @PathVariable("id") long id)
    {
        try
        {
            if (!puntoMaterialRepository.existsById(id))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el punto material!");
            }

            puntoMaterialRepository.save(puntoMaterial);
            return ResponseEntity.status(HttpStatus.OK).body("Se actualizo correctamente!");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error al actualizar!");
        }

    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarMaterial(@PathVariable("id") long id)
    {
        try
        {
            if (!puntoMaterialRepository.existsById(id))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el punto material!");
            }

            puntoMaterialRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente!");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error al eliminar!");
        }
    }
}

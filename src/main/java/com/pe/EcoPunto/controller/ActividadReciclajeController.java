package com.pe.EcoPunto.controller;

import com.pe.EcoPunto.entity.ActividadReciclaje;
import com.pe.EcoPunto.repository.ActividadReciclajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/rest/v1/actividadReciclaje")
public class ActividadReciclajeController
{
    @Autowired
    private ActividadReciclajeRepository actividadReciclajeRepository;

    @GetMapping("listar")
    public ResponseEntity<?> listarActividadesReciclajes()
    {
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(actividadReciclajeRepository.findAll());
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error, por favor intente más tarde");
        }
    }

    @GetMapping("listar/{id}")
    public ResponseEntity<?> listarActividadReciclaje(@PathVariable("id") long id)
    {
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(actividadReciclajeRepository.findById(id).orElse(null));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error, por favor intente más tarde");
        }
    }

    @PostMapping("guardar")
    public ResponseEntity<?> guardarActividadReciclaje(@RequestBody ActividadReciclaje actividadReciclaje)
    {
        try
        {
            if (actividadReciclaje == null || (actividadReciclaje.getActividadReciclajeId() != null && actividadReciclajeRepository.existsById(actividadReciclaje.getActividadReciclajeId())))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se puede guardar un Actividad Reciclaje existente o no válido!");
            }

            actividadReciclajeRepository.save(actividadReciclaje);
            return ResponseEntity.status(HttpStatus.OK).body("Se guardo correctamente!");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error al guardar!");
        }
    }

    @PutMapping("actualizar/{id}")
    public ResponseEntity<?> actualizarMaterialReciclable(@RequestBody ActividadReciclaje actividadReciclaje, @PathVariable("id") long id)
    {
        try
        {
            if (!actividadReciclajeRepository.existsById(id))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el Actividad Reciclaje!!");
            }

            actividadReciclajeRepository.save(actividadReciclaje);
            return ResponseEntity.status(HttpStatus.OK).body("Se actualizo correctamente!");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error al actualizar!");
        }

    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarMaterialReciclable(@PathVariable("id") long id)
    {
        try
        {
            if (!actividadReciclajeRepository.existsById(id))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el Actividad Reciclaje!");
            }

            actividadReciclajeRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente!");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error al eliminar!");
        }
    }
}

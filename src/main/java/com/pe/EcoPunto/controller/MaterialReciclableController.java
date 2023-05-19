package com.pe.EcoPunto.controller;

import com.pe.EcoPunto.entity.materiales_reciclables;
import com.pe.EcoPunto.repository.MaterialReciclableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/rest/v1/materialReciclable")
public class MaterialReciclableController
{
    @Autowired
    private MaterialReciclableRepository materialReciclableRepository;

    @GetMapping("listar")
    public ResponseEntity<?> listarMaterialReciclable()
    {
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(materialReciclableRepository.findAll());
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error, por favor intente más tarde");
        }
    }

    @GetMapping("listar/{id}")
    public ResponseEntity<?> listarMaterialReciclable(@PathVariable("id") long id)
    {
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(materialReciclableRepository.findById(id).orElse(null));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error, por favor intente más tarde");
        }
    }

    @PostMapping("guardar")
    public ResponseEntity<?> guardarMaterialReciclable(@RequestBody materiales_reciclables materialReciclable)
    {
        try
        {
            if (materialReciclable == null || (materialReciclable.getMaterialesReciclablesId() != null && materialReciclableRepository.existsById(materialReciclable.getMaterialesReciclablesId())))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se puede guardar un Material Reciclable existente o no válido!");
            }

            materialReciclableRepository.save(materialReciclable);
            return ResponseEntity.status(HttpStatus.OK).body("Se guardo correctamente!");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error al guardar!");
        }
    }

    @PutMapping("actualizar/{id}")
    public ResponseEntity<?> actualizarMaterialReciclable(@RequestBody materiales_reciclables materialesReciclables, @PathVariable("id") long id)
    {
        try
        {
            if (!materialReciclableRepository.existsById(id))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el Material Reciclable!");
            }

            materialReciclableRepository.save(materialesReciclables);
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
            if (!materialReciclableRepository.existsById(id))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el Material Reciclable!");
            }

            materialReciclableRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente!");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error al eliminar!");
        }
    }
}

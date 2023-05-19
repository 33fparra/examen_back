package com.pe.EcoPunto.controller;

import com.pe.EcoPunto.entity.roles;
import com.pe.EcoPunto.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/rest/v1/rol")
public class RolesController
{
    @Autowired
    private RolesRepository rolesRepository;

    @GetMapping("listar")
    public ResponseEntity<?> listarRoles()
    {
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(rolesRepository.findAll());
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error, por favor intente más tarde");
        }
    }

    @GetMapping("listar/{id}")
    public ResponseEntity<?> listarRol(@PathVariable("id") long id)
    {
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(rolesRepository.findById(id).orElse(null));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error, por favor intente más tarde");
        }
    }

    @PostMapping("guardar")
    public ResponseEntity<?> guardarRol(@RequestBody roles rol)
    {
        try
        {
            if (rol == null || (rol.getId()!=null && rolesRepository.existsById(rol.getId())))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se puede guardar un Rol existente o no válido!");
            }

            rolesRepository.save(rol);
            return ResponseEntity.status(HttpStatus.OK).body("Se guardo correctamente!");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error al guardar!");
        }
    }

    @PutMapping("actualizar/{id}")
    public ResponseEntity<?> actualizarRol(@RequestBody roles rolNuevo, @PathVariable("id") long id)
    {
        try
        {
            //roles rolAntiguo = rolesRepository.findById(id).orElse(null);
            //if (rolAntiguo == null)
            if (!rolesRepository.existsById(id))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el rol!");
            }

            rolNuevo.setId(id);
            rolesRepository.save(rolNuevo);
            return ResponseEntity.status(HttpStatus.OK).body("Se actualizo correctamente!");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error al actualizar!");
        }

    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarRol(@PathVariable("id") long id)
    {
        try
        {
            if (!rolesRepository.existsById(id))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el rol!");
            }

            rolesRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente!");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error al eliminar!");
        }
    }
}

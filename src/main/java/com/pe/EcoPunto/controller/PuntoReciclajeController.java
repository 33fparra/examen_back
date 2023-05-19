package com.pe.EcoPunto.controller;

import com.pe.EcoPunto.DTO.PuntoReciclajeDTO;
import com.pe.EcoPunto.entity.PuntoReciclaje;
import com.pe.EcoPunto.entity.usuarios;
import com.pe.EcoPunto.repository.PuntoReciclajeRepository;
import com.pe.EcoPunto.repository.usuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/rest/v1/puntoReciclaje")
@CrossOrigin("*")
public class PuntoReciclajeController
{
    @Autowired
    private PuntoReciclajeRepository puntoReciclajeRepository;

    @Autowired
    private usuariosRepository usuariosRepositorys;

    @GetMapping("/listar")
    public ResponseEntity<?> listarPuntosReciclajes()
    {
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(puntoReciclajeRepository.findAll());
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error, por favor intente más tarde");
        }
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<?> listarPuntoReciclaje(@PathVariable("id") long id)
    {
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(puntoReciclajeRepository.findById(id).orElse(null));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error, por favor intente más tarde");
        }
    }
    @PostMapping("/guardar")
    public ResponseEntity<?> guardarPuntoReciclaje(@RequestBody PuntoReciclajeDTO puntoReciclaje)
    {
        Map<String, Object> msg = new HashMap<>();
        try
        {
            usuarios xd = usuariosRepositorys.findById(puntoReciclaje.getUsuario_id()).orElse(null);
            if (xd == null)
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se puede guardar un Punto Reciclaje existente o no válido!");
            }

            PuntoReciclaje puntoReciclajeSave = new PuntoReciclaje();
            puntoReciclajeSave.setNombre(puntoReciclaje.getNombre());
            puntoReciclajeSave.setDireccion(puntoReciclaje.getDireccion());
            puntoReciclajeSave.setHorarioAtencion(puntoReciclaje.getHorarioAtencion());
            puntoReciclajeSave.setLatitud(puntoReciclaje.getLatitud());
            puntoReciclajeSave.setLongitud(puntoReciclaje.getLongitud());
            puntoReciclajeSave.setTelefono(puntoReciclaje.getTelefono());
            puntoReciclajeSave.setUsuario(xd);
            puntoReciclajeRepository.save(puntoReciclajeSave);
            msg.put("mensaje", "Se guardo correctamente!");
            return ResponseEntity.status(HttpStatus.OK).body(msg);
        }
        catch (Exception e)
        {
            msg.put("mensaje", "Ocurrió un Error, intenta de nuevo!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
    }

    @PutMapping("actualizar/{id}")
    public ResponseEntity<?> actualizarReciclaje(@RequestBody PuntoReciclaje puntoReciclaje, @PathVariable("id") long id)
    {
        try
        {
            if (!puntoReciclajeRepository.existsById(id))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el punto reciclaje!");
            }

            puntoReciclajeRepository.save(puntoReciclaje);
            return ResponseEntity.status(HttpStatus.OK).body("Se actualizo correctamente!");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrió un error al actualizar!");
        }

    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarReciclaje(@PathVariable("id") long id)
    {
        Map<String, String> msg = new HashMap<>();
        try
        {
            if (!puntoReciclajeRepository.existsById(id))
            {
                msg.put("mensaje", "No existe el punto reciclaje");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
            }

            puntoReciclajeRepository.deleteById(id);
            msg.put("mensaje", "Se elimino correctamente!");
            return ResponseEntity.status(HttpStatus.OK).body(msg);
        }
        catch (Exception e)
        {
            msg.put("mensaje", "Ocurrió un Error, intenta de nuevo!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
    }
}

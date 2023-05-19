package com.pe.EcoPunto.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Map;

import javax.transaction.Transactional;

import com.pe.EcoPunto.Util.RandomText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pe.EcoPunto.entity.roles;
import com.pe.EcoPunto.entity.usuarios;
import com.pe.EcoPunto.repository.RolesRepository;
import com.pe.EcoPunto.repository.usuariosRepository;
@RestController
@RequestMapping("/rest/v1/usuarios")
@CrossOrigin("*")
public class usuariosController {
    @Autowired
    private usuariosRepository usuRepo;

    @Autowired
    private RolesRepository rolRepo;

    @GetMapping("/listar/todo")
    public List<Map<String, Object>> ListarTodoUsuariosConRoles() {
        List<usuarios> listaUsuarios = usuRepo.findAll();
        List<Map<String, Object>> listaUsuariosConRoles = new ArrayList<>();
        for (usuarios usuario : listaUsuarios) {
            Map<String, Object> usuarioConRol = new HashMap<>();
            usuarioConRol.put("id", usuario.getId());
            usuarioConRol.put("nombre", usuario.getNombre());
            usuarioConRol.put("correoElectronico", usuario.getCorreoElectronico());
            usuarioConRol.put("contrasena", usuario.getContrasena());
            usuarioConRol.put("telefono", usuario.getTelefono());
            usuarioConRol.put("direccion", usuario.getDireccion());
            usuarioConRol.put("rol", usuario.getRol().getNombre());
            listaUsuariosConRoles.add(usuarioConRol);
        }
        return listaUsuariosConRoles;
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<?> obtenerUsuarioConRolId(@PathVariable Long id) {
        Map<String, Object> msg = new HashMap<>();
        try {
            Optional<usuarios> usuarioOptional = usuRepo.findById(id);
            if (usuarioOptional.isPresent()) {
                usuarios usuario = usuarioOptional.get();
                usuario.getRol().getNombre(); // Obtener nombre del rol
                return ResponseEntity.ok(usuario);
            } else {
                msg.put("mensaje", "el id del usuario no existe");
                return ResponseEntity.badRequest().body(msg);
            }
        } catch (Exception e) {
            msg.put("mensaje", "Error Vuelve a intentarlo");
            System.out.println("Error aqui -> obtenerUsuarioConRolId");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
        }
    }

    @PostMapping("/crearUsuario")
    @Transactional
    public ResponseEntity<Map<String, Object>> crearUsuarioConRol(@RequestBody usuarios usuario) {
        Map<String, Object> msg = new HashMap<>();
        try {
           boolean validarCorreo = usuRepo.existsByCorreoElectronico(usuario.getCorreoElectronico());

            if (validarCorreo == true) {
                msg.put("mensaje", "El correo electrónico ya está registrado");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
            }
            roles rol = rolRepo.findByNombre(usuario.getRol().getNombre())
                    .orElseGet(() -> {
                        roles newRol = new roles();
                        // roles newRol = new roles(usuario.getRol().getNombre()); //agregado
                        newRol.setNombre(usuario.getRol().getNombre());
                        return rolRepo.save(newRol);
                    });
            usuario.setRol(rol);
            usuRepo.save(usuario);
            msg.put("mensaje", "Registro de Usuario Exitoso");
            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            msg.put("mensaje", "Error Al crear usuario");
            System.out.println("Error aqui -> crearUsuarioConRol");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
        }
    }

    @PutMapping("/editar/{id}")
    @Transactional
    public ResponseEntity<?> modificarUsuarioConRol(@PathVariable Long id,
            @RequestBody usuarios usuario) {
        Map<String, Object> msg = new HashMap<>();
        try {
            Optional<usuarios> usuarioOptional = usuRepo.findById(id);
            if (usuarioOptional.isPresent()) {
                usuarios usuarioExistente = usuarioOptional.get();
                if (!usuarioExistente.getCorreoElectronico().equals(usuario.getCorreoElectronico())
                        && usuRepo.existsByCorreoElectronico(usuario.getCorreoElectronico())) {
                    msg.put("mensaje", "El correo electronico ya esta en uso");
                    return ResponseEntity.badRequest().body(msg);
                }
                // buscamos rol
                Optional<roles> validarRol = rolRepo.findByNombre(usuario.getRol().getNombre());
                if (validarRol.isEmpty()) {
                    msg.put("mensaje", "El rol especificado no existe");
                    return ResponseEntity.badRequest().body(msg);
                }
                usuarioExistente.setNombre(usuario.getNombre());
                usuarioExistente.setCorreoElectronico(usuario.getCorreoElectronico());
                usuarioExistente.setContrasena(usuario.getContrasena());
                usuarioExistente.setTelefono(usuario.getTelefono());
                usuarioExistente.setDireccion(usuario.getDireccion());
                usuarioExistente.setRol(validarRol.get());

                // usuarios usuarioActualizado = usuRepo.save(usuarioExistente);
                usuRepo.save(usuarioExistente);
                msg.put("mensaje", "Usuario Actualizado Correctamente");
                return ResponseEntity.ok(msg);
            } else {
                msg.put("mensaje", "El usuario no existe");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
            }
        } catch (Exception e) {
            msg.put("mensaje", "Error al actualizar usuario");
            System.out.println("Error aqui -> modificarUsuarioConRol");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    @Transactional
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        Map<String, Object> msg = new HashMap<>();
        try {
            Optional<usuarios> usuarioOptional = usuRepo.findById(id);
            if (usuarioOptional.isPresent()) {
                usuRepo.deleteById(id);
                msg.put("mensaje", "Usuario eliminado correctamente");
                return ResponseEntity.ok(msg);
            } else {
                msg.put("mensaje", "El usuario no existe");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
            }
        } catch (Exception e) {
            msg.put("mensaje", "Error al eliminar usuario");
            System.out.println("Error aqui -> eliminarUsuario");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String correoElectronico = body.get("correoElectronico");
        String contrasena = body.get("contrasena");
        Map<String, String> msg = new HashMap<>();
        System.out.println(correoElectronico);
        System.out.println(contrasena);

        Optional<usuarios> usuarioOptional = usuRepo.findByCorreoElectronico(correoElectronico);
        if (usuarioOptional.isPresent()) {
            usuarios usuario = usuarioOptional.get();
            System.out.println("------------");
            //System.out.println(usuario);
            System.out.println("------------");
            if (usuario.getContrasena().equals(contrasena)) {
                msg.put("mensaje", "Bienvenido " + usuario.getRol().getNombre() + " : " + usuario.getNombre());
                msg.put("tipUser", usuario.getRol().getNombre());
                return ResponseEntity.ok(msg);
                // return ResponseEntity.ok().build();
            }
        }
        msg.put("mensaje", " la contraseña o el usuario no existe");
        // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(msg);

    }

    @PostMapping("/registrarse")
    public ResponseEntity<Map<String, Object>> registrarUsuario(@RequestBody usuarios usuario) {
        Map<String, Object> msg = new HashMap<>();
        try {
           // usuario.setCorreoElectronico(RandomText.generateRandomString(9));
            if (usuRepo.existsByCorreoElectronico(usuario.getCorreoElectronico())) {
                msg.put("mensaje", "El correo electronico ya esta en uso");
                return ResponseEntity.ok(msg);
            }
            // Verificar si el rol existe si no existe creara con el rol "Usuario"
            Optional<roles> rolOptional = rolRepo.findByNombre(usuario.getRol().getNombre());
            roles rol;
            if (rolOptional.isPresent()) {
                rol = rolOptional.get();
            } else {
                Optional<roles> rolUsuario = rolRepo.findByNombre("Usuario");
                if (rolUsuario.isPresent()) {
                    rol = rolUsuario.get();
                } else {
                    rol = new roles("Usuario");
                    rolRepo.save(rol);
                }
            }

            usuarios listusuario = new usuarios(usuario.getNombre(), usuario.getCorreoElectronico(),
                    usuario.getContrasena(),usuario.getTelefono(), usuario.getDireccion(),  rol);
            usuRepo.save(listusuario);

            msg.put("mensaje", "Usuario registrado correctamente");
            System.out.println(msg);
            return ResponseEntity.ok(msg);

        } catch (Exception e) {
            msg.put("mensaje", "Error al registrar usuario");
            //msg.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
        }
    }
}

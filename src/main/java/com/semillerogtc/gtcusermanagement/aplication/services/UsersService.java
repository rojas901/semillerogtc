package com.semillerogtc.gtcusermanagement.aplication.services;

import com.semillerogtc.gtcusermanagement.domain.*;
import com.semillerogtc.gtcusermanagement.domain.components.JWTManagerService;
import com.semillerogtc.gtcusermanagement.domain.components.UsersValidation;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsersService {

    UsersValidation _usersValidation;
    UsuariosRepositorio _usuariosRepositorio;

    JWTManagerService _jwtManagerService;

    UsersService(UsersValidation usersValidation, UsuariosRepositorio usuariosRepositorio, JWTManagerService jwtManagerService) {
        _usuariosRepositorio = usuariosRepositorio;
        _usersValidation = usersValidation;
        _jwtManagerService = jwtManagerService;
    }

    public List<Usuario> consultarUsuarios() {
        List<Usuario> usuarios = _usuariosRepositorio.findAll();
        return usuarios;
    }

    public Optional<Usuario> consultarUsuarioById(String id) {

        Optional<Usuario> usuario = _usuariosRepositorio.findById(id);
        return usuario;
    }

    public Usuario consultarUsuarioByEmail(String email) {
        return _usuariosRepositorio.findByEmail(new Email(email));
    }

    public UsuarioCreadoDto registrarUsuario(UsuarioNuevoDto usuarioNuevoDto) {
        //Usuario usuario = Usuario.builder().email("j").build();
        boolean resultado = _usersValidation.execute(usuarioNuevoDto);

        Usuario usuarioNuevo = new Usuario();
        usuarioNuevo.setNombre(usuarioNuevoDto.getNombre());
        usuarioNuevo.setEmail(new Email(usuarioNuevoDto.getEmail()));
        usuarioNuevo.setPassword(usuarioNuevoDto.getPassword());
        usuarioNuevo.setLastAccess(new Date());
        usuarioNuevo.setToken(_jwtManagerService.generate(usuarioNuevoDto.getEmail()));
        usuarioNuevo.setActivo(true);

        List<Telefono> telefonos = usuarioNuevoDto.getTelefonos();
        Set<UsuarioTelefono> telefonosSet = new HashSet<>();

        for (Telefono telefono : telefonos) {
            UsuarioTelefono usuarioTelefono = new UsuarioTelefono();
            usuarioTelefono.setTelefono(telefono);
            telefonosSet.add(usuarioTelefono);
        }

        usuarioNuevo.setTelefonos(telefonosSet);

        Usuario usuarioRegistrado = _usuariosRepositorio.save(usuarioNuevo);

        UsuarioCreadoDto usuarioCreadoDto = new UsuarioCreadoDto();

        usuarioCreadoDto.setId(usuarioRegistrado.getId());
        usuarioCreadoDto.setCreatedAt(usuarioRegistrado.getCreatedAt());
        usuarioCreadoDto.setModifyAt(usuarioRegistrado.getModifyAt());
        usuarioCreadoDto.setLastAccess(usuarioRegistrado.getLastAccess());
        usuarioCreadoDto.setToken(usuarioRegistrado.getToken());
        usuarioCreadoDto.setActivo(usuarioRegistrado.isActivo());


        return usuarioCreadoDto;
    }

    public Usuario actualizarUsuario(String id, UsuarioNuevoDto usuarioNuevoDto) {
        Usuario usuarioNuevo = _usuariosRepositorio.getReferenceById(id);

        usuarioNuevo.setNombre(usuarioNuevoDto.getNombre());
        //usuarioNuevo.setPassword(usuarioNuevoDto.getPassword());

        Usuario usuarioActualizado = _usuariosRepositorio.save(usuarioNuevo);

        return usuarioActualizado;
    }

    public void eliminarUsuario(String userId) {
        _usuariosRepositorio.deleteById(userId);
    }

}

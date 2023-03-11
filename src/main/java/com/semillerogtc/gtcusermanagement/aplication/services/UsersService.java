package com.semillerogtc.gtcusermanagement.aplication.services;

import com.semillerogtc.gtcusermanagement.domain.UsuarioDto;
import com.semillerogtc.gtcusermanagement.domain.UsuariosRepositorio;
import com.semillerogtc.gtcusermanagement.domain.components.UsersValidation;
import com.semillerogtc.gtcusermanagement.domain.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    UsersValidation _usersValidation;
    UsuariosRepositorio _usuariosRepositorio;

    UsersService(UsersValidation usersValidation, UsuariosRepositorio usuariosRepositorio) {
        _usuariosRepositorio = usuariosRepositorio;
        _usersValidation = usersValidation;
    }

    public List<Usuario> consultarUsuarios() {
        List<Usuario> usuarios = _usuariosRepositorio.findAll();
        return usuarios;
    }

    public Optional<Usuario> consultarUsuario(String id) {

        Optional<Usuario> usuario = _usuariosRepositorio.findById(id);
        return usuario;
    }

    public Usuario registrarUsuario(UsuarioDto usuarioDto) {
        //Usuario usuario = Usuario.builder().email("j").build();
        boolean resultado = _usersValidation.execute(usuarioDto);

        Usuario usuarioARegistrar = new Usuario();
        usuarioARegistrar.setNombre(usuarioDto.getNombre());
        usuarioARegistrar.setEmail(usuarioDto.getEmail());
        usuarioARegistrar.setEdad(usuarioDto.getEdad());
        usuarioARegistrar.setCelular(usuarioDto.getCelular());

        Usuario usuarioRegistrado = _usuariosRepositorio.save(usuarioARegistrar);

        return usuarioRegistrado;
    }

    public Usuario actualizarUsuario(String id, UsuarioDto usuarioDto) {
        Usuario usuarioAActualizar = _usuariosRepositorio.getReferenceById(id);

        usuarioAActualizar.setNombre(usuarioDto.getNombre());
        usuarioAActualizar.setEmail(usuarioDto.getEmail());
        usuarioAActualizar.setEdad(usuarioDto.getEdad());
        usuarioAActualizar.setCelular(usuarioDto.getCelular());

        Usuario usuarioActualizado = _usuariosRepositorio.save(usuarioAActualizar);

        return usuarioActualizado;
    }

    public void eliminarUsuario(String userId) {
        _usuariosRepositorio.deleteById(userId);
    }
}

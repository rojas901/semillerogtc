package com.semillerogtc.gtcusermanagement.aplication.services;

import com.semillerogtc.gtcusermanagement.domain.*;
import com.semillerogtc.gtcusermanagement.domain.components.JWTManagerService;
import com.semillerogtc.gtcusermanagement.domain.components.PasswordEncoderService;
import com.semillerogtc.gtcusermanagement.domain.components.UsersValidation;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsersService {

    //UsersValidation _usersValidation;
    UsuariosRepositorio _usuariosRepositorio;
    JWTManagerService _jwtManagerService;

    PasswordEncoderService _passwordEncoderService;

    UsersService(UsuariosRepositorio usuariosRepositorio, JWTManagerService jwtManagerService, PasswordEncoderService passwordEncoderService) {
        _usuariosRepositorio = usuariosRepositorio;
        //_usersValidation = usersValidation;
        _jwtManagerService = jwtManagerService;
        _passwordEncoderService = passwordEncoderService;
    }

    public List<Usuario> consultarUsuarios() {
        List<Usuario> usuarios = _usuariosRepositorio.findAll();
        return usuarios;
    }

    public Optional<Usuario> consultarUsuarioById(String id) {

        Optional<Usuario> usuario = _usuariosRepositorio.findById(id);

        if (usuario.isEmpty()) {
            throw new InvalidIdException();
        }

        return usuario;
    }

    public Usuario consultarUsuarioByEmail(String email) {

        Usuario usuarioDB = _usuariosRepositorio.findByEmail(new Email(email));

        if (usuarioDB == null) {
            throw new InvalidEmailException();
        }

        return usuarioDB;
    }

    public Usuario consultarUsuarioByNombre(String nombre) {

        Usuario usuarioDB = _usuariosRepositorio.findByNombre(nombre.toLowerCase());

        if (usuarioDB == null) {
            throw new InvalidNameException();
        }

        return usuarioDB;
    }

    public UsuarioCreadoDto registrarUsuario(UsuarioNuevoDto usuarioNuevoDto, String secret) {
        //Usuario usuario = Usuario.builder().email("j").build();
        //boolean resultado = _usersValidation.execute(usuarioNuevoDto);

        if (_usuariosRepositorio.findByEmail(new Email(usuarioNuevoDto.getEmail())) != null) {
            throw new InvalidEmailExistException();
        }

        Usuario usuarioNuevo = new Usuario();
        usuarioNuevo.setNombre(usuarioNuevoDto.getNombre().toLowerCase());
        usuarioNuevo.setEmail(new Email(usuarioNuevoDto.getEmail()));
        usuarioNuevo.setPassword(_passwordEncoderService.encode(usuarioNuevoDto.getPassword()));
        usuarioNuevo.setLastAccess(new Date());
        usuarioNuevo.setToken(_jwtManagerService.generate(usuarioNuevoDto.getEmail(), secret));
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

    public String login(UsuarioLoginDto usuarioLoginDto) {

        Usuario usuarioDB = _usuariosRepositorio.findByEmail(new Email(usuarioLoginDto.getEmail()));

        if (usuarioDB == null) {
            throw new InvalidUserException();
        }

        boolean validarPassword = _passwordEncoderService.validarPassword(usuarioLoginDto.getPassword(), usuarioDB.getPassword());

        if (validarPassword) {
            usuarioDB.setLastAccess(new Date());
            _usuariosRepositorio.save(usuarioDB);
            return usuarioDB.getToken();
        } else {
            throw new InvalidUserException();
        }
    }

    public Usuario actualizarUsuario(String id, UsuarioNuevoDto usuarioNuevoDto, String secret) {
        Usuario usuarioNuevo = _usuariosRepositorio.getReferenceById(id);

        if (usuarioNuevo == null) {
            throw new InvalidIdException();
        }

        if (_usuariosRepositorio.findByEmail(new Email(usuarioNuevoDto.getEmail())) != null) {
            throw new InvalidEmailExistException();
        }

        usuarioNuevo.setEmail(new Email(usuarioNuevoDto.getEmail()));

        usuarioNuevo.setPassword(_passwordEncoderService.encode(usuarioNuevoDto.getPassword()));

        usuarioNuevo.setToken(_jwtManagerService.generate(usuarioNuevoDto.getEmail(), secret));

        Usuario usuarioActualizado = _usuariosRepositorio.save(usuarioNuevo);

        return usuarioActualizado;
    }

    public void eliminarUsuario(String userId) {
        _usuariosRepositorio.deleteById(userId);
    }

}

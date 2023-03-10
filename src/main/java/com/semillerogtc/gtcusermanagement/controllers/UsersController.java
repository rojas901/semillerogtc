package com.semillerogtc.gtcusermanagement.controllers;

import com.semillerogtc.gtcusermanagement.common.EnviromentService;
import com.semillerogtc.gtcusermanagement.domain.Usuario;
import com.semillerogtc.gtcusermanagement.domain.UsuarioDto;
import com.semillerogtc.gtcusermanagement.domain.UsuarioDto2;
import com.semillerogtc.gtcusermanagement.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("usuarios")
public class UsersController {

    //@Autowired //inyeccion de depencia por atributo de clase, hace dificil pruebas unitarias
    UsersService _user;

    EnviromentService _enviromentService;

    public final Logger logger = LoggerFactory.getLogger(UsersController.class);

    //inyeccion por constructor es la forma adecuada, para reliazar pruebas unitarias
    UsersController(UsersService user, @Qualifier("qaEnviromentService") EnviromentService enviromentService) {
        logger.info("Se inicializa en el constructor");
        _user = user;
        _enviromentService = enviromentService;
        logger.info(_enviromentService.getEnviromentName());
    }

    /*@Autowired //inyeccion por metodo seeter, es parecido a inyeccion por constructor
    public void inicializar(UsersService user) {
        logger.info("Se ejecuta metodo inicializar"); //se usa para generar logs en la consola con spring
        _user = user;
    }*/
    //headers
    @GetMapping("ping")
    public String consultarUsuario1(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        logger.info(token);
        return "Hola desde el controlador de usuarios";
    }
    //queryparams
    @GetMapping
    public Boolean consultarUsuario2(@RequestParam String nombre, @RequestParam String apellido) {
        logger.info(nombre + " " + apellido);
        Usuario user = new Usuario();
        user.nombre = "Jeff";
        return _user.registrarUsuario(user);
    }
    //params uritemplate
    @GetMapping("consultar/{nombre}/{apellido}")
    public Boolean consultarUsuario3(@PathVariable("nombre") String nombre, @PathVariable("apellido")String apellido) {
        logger.info(nombre + " " + apellido);
        Usuario user = new Usuario();
        user.nombre = "Jeff";
        return _user.registrarUsuario(user);
    }

    @PostMapping("/ping")
    @ResponseStatus(code=HttpStatus.CREATED, reason="")
    public Boolean registrarUsuario(@RequestBody UsuarioDto usuarioDto) {
        logger.info(usuarioDto.nombre + " " + usuarioDto.apellido);
        Usuario user = new Usuario();
        user.nombre = "Jeff";
        return _user.registrarUsuario(user);
    }

    @PostMapping("/ping2")
    public ResponseEntity registrarUsuario(@RequestBody UsuarioDto2 usuarioDto) {
        return new ResponseEntity<UsuarioDto2>(usuarioDto, HttpStatus.CREATED);
    }

    @PatchMapping("v1/ping/{id}")
    public UsuarioDto actualizarUsuario(@Valid @RequestBody UsuarioDto usuarioDto) {
        return usuarioDto;
    }

    @PatchMapping("v2/ping/{id}")
    public UsuarioDto2 actualizarUsuario(@Valid @RequestBody UsuarioDto2 usuarioDto) {
        return usuarioDto;
    }

    @DeleteMapping("/ping/{id}")
    public int borrarUsuario(@PathVariable("id") int id) {
        return id;
    }

}

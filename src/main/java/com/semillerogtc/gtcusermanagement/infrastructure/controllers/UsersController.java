package com.semillerogtc.gtcusermanagement.infrastructure.controllers;

import com.semillerogtc.gtcusermanagement.common.EnviromentService;
import com.semillerogtc.gtcusermanagement.domain.Usuario;
import com.semillerogtc.gtcusermanagement.domain.UsuarioDto;
import com.semillerogtc.gtcusermanagement.domain.UsuarioDto2;
import com.semillerogtc.gtcusermanagement.aplication.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    UsersService _userService;

    EnviromentService _enviromentService;

    public final Logger logger = LoggerFactory.getLogger(UsersController.class);

    //inyeccion por constructor es la forma adecuada, para reliazar pruebas unitarias
    UsersController(UsersService user, @Qualifier("qaEnviromentService") EnviromentService enviromentService) {
        logger.info("Se inicializa en el constructor");
        _userService = user;
        _enviromentService = enviromentService;
        logger.info(_enviromentService.getEnviromentName());
    }

    /*@Autowired //inyeccion por metodo seeter, es parecido a inyeccion por constructor
    public void inicializar(UsersService user) {
        logger.info("Se ejecuta metodo inicializar"); //se usa para generar logs en la consola con spring
        _user = user;
    }*/

    @GetMapping
    public ResponseEntity consultarUsuarios() {
        return new ResponseEntity<>(_userService.consultarUsuarios(), HttpStatus.OK);
    }

    //params uritemplate
    @GetMapping("/{id}")
    public ResponseEntity consultarUsuario3(@PathVariable("id")String userId) {
        return new ResponseEntity(_userService.consultarUsuario(userId), HttpStatus.OK);
    }

    //headers
    @GetMapping("ping")
    public String consultarUsuario1(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        logger.info(token);
        return "Hola desde el controlador de usuarios";
    }
    //queryparams
    @GetMapping("consultar")
    public ResponseEntity consultarUsuario2(@RequestParam String email) {
        logger.info(email);
        UsuarioDto usuario = UsuarioDto.builder().email(email).build();
        return new ResponseEntity(_userService.registrarUsuario(usuario),
                HttpStatus.OK);
    }

    //@ResponseStatus(code=HttpStatus.CREATED, reason="")
    @PostMapping("v1")
    public ResponseEntity registrarUsuario(@RequestBody UsuarioDto usuarioDto) {
        logger.info(usuarioDto.getEmail() + "- " + usuarioDto.getCelular());
        try{
            Usuario usuarioRegistrado = _userService.registrarUsuario(usuarioDto);

            return new ResponseEntity(usuarioRegistrado, HttpStatus.CREATED);
        } catch(Exception ex) {
            return new ResponseEntity("Falló la creación de usuario, Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("v2")
    public ResponseEntity registrarUsuario2(@Valid @RequestBody UsuarioDto usuarioDto) {
        Usuario usuarioRegistrado = _userService.registrarUsuario(usuarioDto);

        return new ResponseEntity(usuarioRegistrado, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity actualizarUsuario(@PathVariable("id") String id, @RequestBody UsuarioDto usuarioDto) {
        try{
            return new ResponseEntity(_userService.actualizarUsuario(id, usuarioDto), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarUsuario(@PathVariable("id") String id) {

        try {
            _userService.eliminarUsuario(id);
            return new ResponseEntity("Usuario elminado con exito", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.NO_CONTENT);
        }
    }

}

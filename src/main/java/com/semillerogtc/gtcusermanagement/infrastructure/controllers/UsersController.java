package com.semillerogtc.gtcusermanagement.infrastructure.controllers;

import com.semillerogtc.gtcusermanagement.domain.UsuarioCreadoDto;
import com.semillerogtc.gtcusermanagement.infrastructure.environment.EnviromentService;
import com.semillerogtc.gtcusermanagement.domain.Usuario;
import com.semillerogtc.gtcusermanagement.domain.UsuarioNuevoDto;
import com.semillerogtc.gtcusermanagement.aplication.services.UsersService;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
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
    public ResponseEntity consultarUsuarioById(@PathVariable("id")String userId) {
        return new ResponseEntity(_userService.consultarUsuarioById(userId), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity consultarUsuarioByEmail(@PathVariable("email")String email) {
        return new ResponseEntity(_userService.consultarUsuarioByEmail(email), HttpStatus.OK);
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
        UsuarioNuevoDto usuario = UsuarioNuevoDto.builder().email(email).build();
        return new ResponseEntity(_userService.registrarUsuario(usuario),
                HttpStatus.OK);
    }

    //@ResponseStatus(code=HttpStatus.CREATED, reason="")
    @PostMapping("v1")
    public ResponseEntity registrarUsuario(@Valid @RequestBody UsuarioNuevoDto usuarioNuevoDto) {
        //logger.info(usuarioNuevoDto.getEmail());
        try {
            Usuario usuarioExiste = _userService.consultarUsuarioByEmail(usuarioNuevoDto.getEmail());
            if (usuarioExiste == null) {
                UsuarioCreadoDto usuarioCreadoDto = _userService.registrarUsuario(usuarioNuevoDto);

                return new ResponseEntity(usuarioCreadoDto, HttpStatus.CREATED);
            }
            return new ResponseEntity("El email, ya existe", HttpStatus.BAD_REQUEST);

        } catch(Exception ex) {
            return new ResponseEntity("Falló la creación de usuario, Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*@PostMapping("v2")
    public ResponseEntity registrarUsuario2(@Valid @RequestBody UsuarioNuevoDto usuarioNuevoDto) {
        Usuario usuarioRegistrado = _userService.registrarUsuario(usuarioNuevoDto);

        return new ResponseEntity(usuarioRegistrado, HttpStatus.CREATED);
    }*/

    @PatchMapping("/{id}")
    public ResponseEntity actualizarUsuario(@PathVariable("id") String id, @RequestBody UsuarioNuevoDto usuarioNuevoDto) {
        try{
            return new ResponseEntity(_userService.actualizarUsuario(id, usuarioNuevoDto), HttpStatus.OK);
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

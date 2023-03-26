package com.semillerogtc.gtcusermanagement.infrastructure.controllers;

import com.semillerogtc.gtcusermanagement.domain.UsuarioCreadoDto;
import com.semillerogtc.gtcusermanagement.domain.UsuarioLoginDto;
import com.semillerogtc.gtcusermanagement.domain.components.JWTManagerService;
import com.semillerogtc.gtcusermanagement.infrastructure.environment.EnviromentService;
import com.semillerogtc.gtcusermanagement.domain.Usuario;
import com.semillerogtc.gtcusermanagement.domain.UsuarioNuevoDto;
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
@CrossOrigin("*")
@RequestMapping("usuarios")
public class UsersController {

    //@Autowired //inyeccion de depencia por atributo de clase, hace dificil pruebas unitarias
    UsersService _userService;

    EnviromentService _enviromentService;

    JWTManagerService _jwtManagerService;

    public final Logger logger = LoggerFactory.getLogger(UsersController.class);

    //inyeccion por constructor es la forma adecuada, para reliazar pruebas unitarias
    UsersController(UsersService user, @Qualifier("qaEnviromentService") EnviromentService enviromentService, JWTManagerService jwtManagerService) {
        logger.info("Se inicializa en el constructor");
        _userService = user;
        _enviromentService = enviromentService;
        logger.info(_enviromentService.getEnviromentName());
        _jwtManagerService = jwtManagerService;
    }

    /*@Autowired //inyeccion por metodo seeter, es parecido a inyeccion por constructor
    public void inicializar(UsersService user) {
        logger.info("Se ejecuta metodo inicializar"); //se usa para generar logs en la consola con spring
        _user = user;
    }*/

    @GetMapping("v1")
    public ResponseEntity consultarUsuarios(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try{
            _jwtManagerService.validate(token, _enviromentService.getEnviromentSecret());
            return new ResponseEntity(_userService.consultarUsuarios(), HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //params uritemplate
    @GetMapping("v1/id/{id}")
    public ResponseEntity consultarUsuarioById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("id")String userId) {
        try{
            _jwtManagerService.validate(token, _enviromentService.getEnviromentSecret());
            return new ResponseEntity(_userService.consultarUsuarioById(userId), HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("v1/email/{email}")
    public ResponseEntity consultarUsuarioByEmail(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("email")String email) {
        try{
            _jwtManagerService.validate(token, _enviromentService.getEnviromentSecret());
            return new ResponseEntity(_userService.consultarUsuarioByEmail(email), HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("v1/nombre/{nombre}")
    public ResponseEntity consultarUsuarioByName(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("nombre")String nombre) {
        try{
            _jwtManagerService.validate(token, _enviromentService.getEnviromentSecret());
            return new ResponseEntity(_userService.consultarUsuarioByNombre(nombre), HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //headers
    /*@GetMapping("ping")
    public String consultarUsuario1(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        logger.info(token);
        return "Hola desde el controlador de usuarios";
    }*/
    //queryparams
    /*@GetMapping("consultar")
    public ResponseEntity consultarUsuario2(@RequestParam String email) {
        logger.info(email);
        UsuarioNuevoDto usuario = UsuarioNuevoDto.builder().email(email).build();
        return new ResponseEntity(_userService.registrarUsuario(usuario),
                HttpStatus.OK);
    }*/

    //@ResponseStatus(code=HttpStatus.CREATED, reason="")
    @PostMapping("v1/registrarse")
    public ResponseEntity registrarUsuario(@Valid @RequestBody UsuarioNuevoDto usuarioNuevoDto) {
        //logger.info(usuarioNuevoDto.getEmail());
        try {
            UsuarioCreadoDto usuarioCreadoDto = _userService.registrarUsuario(usuarioNuevoDto, _enviromentService.getEnviromentSecret());

            return new ResponseEntity(usuarioCreadoDto, HttpStatus.CREATED);
        } catch(Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("v1/login")
    public ResponseEntity login(@RequestBody UsuarioLoginDto usuarioLoginDto) {
        try {
            return new ResponseEntity(_userService.login(usuarioLoginDto), HttpStatus.OK);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /*@PostMapping("v2")
    public ResponseEntity registrarUsuario2(@Valid @RequestBody UsuarioNuevoDto usuarioNuevoDto) {
        Usuario usuarioRegistrado = _userService.registrarUsuario(usuarioNuevoDto);

        return new ResponseEntity(usuarioRegistrado, HttpStatus.CREATED);
    }*/

    @PatchMapping("v1/{id}")
    public ResponseEntity actualizarUsuario(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("id") String id, @RequestBody UsuarioNuevoDto usuarioNuevoDto) {
        try{
            _jwtManagerService.validate(token, _enviromentService.getEnviromentSecret());
            return new ResponseEntity(_userService.actualizarUsuario(id, usuarioNuevoDto, _enviromentService.getEnviromentSecret()), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("v1/{id}")
    public ResponseEntity eliminarUsuario(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("id") String id) {
        try {
            _jwtManagerService.validate(token, _enviromentService.getEnviromentSecret());
            _userService.eliminarUsuario(id);
            return new ResponseEntity("Usuario elminado con exito", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.NO_CONTENT);
        }
    }

}

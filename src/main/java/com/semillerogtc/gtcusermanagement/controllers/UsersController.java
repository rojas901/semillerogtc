package com.semillerogtc.gtcusermanagement.controllers;

import com.semillerogtc.gtcusermanagement.common.EnviromentService;
import com.semillerogtc.gtcusermanagement.domain.Usuario;
import com.semillerogtc.gtcusermanagement.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuarios")
public class UsersController {

    //@Autowired //inyeccion de depencia por atributo de clase, hace dificil pruebas unitarias
    UsersService _user;

    EnviromentService _enviromentService;
    public final Logger logger = LoggerFactory.getLogger(UsersController.class);

    //inyeccion por constructor es la forma adecuada, para reliazar pruebas unitarias
    UsersController(UsersService user, @Qualifier("qa") EnviromentService enviromentService) {
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

    @GetMapping("ping")
    public String ping() {
        return "Hola desde el controlador de usuarios";
    }

    @PostMapping("ping")
    public Boolean registrarUsuario() {
        Usuario user = new Usuario();
        user.nombre = "Jeff";
        return _user.registrarUsuario(user);
    }
}

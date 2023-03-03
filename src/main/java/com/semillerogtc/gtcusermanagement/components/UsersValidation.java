package com.semillerogtc.gtcusermanagement.components;

import com.semillerogtc.gtcusermanagement.domain.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsersValidation {

    public boolean execute(Usuario user) {

        //return (user == "Jeff") ? true : false; operador ternario

        /*if (user == "Jeffer")
            return true;

          return false;
        */

        return (user.nombre == "Jeff"); //retornar la condicion

    }
}

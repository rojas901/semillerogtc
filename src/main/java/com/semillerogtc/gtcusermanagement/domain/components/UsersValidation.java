package com.semillerogtc.gtcusermanagement.domain.components;

import com.semillerogtc.gtcusermanagement.domain.Usuario;
import com.semillerogtc.gtcusermanagement.domain.UsuarioDto;
import org.springframework.stereotype.Component;

@Component
public class UsersValidation {

    public boolean execute(UsuarioDto user) {

        //return (user == "Jeff") ? true : false; operador ternario

        /*if (user == "Jeffer")
            return true;

          return false;
        */

        return user.getNombre().equals("Jeff"); //retornar la condicion

    }
}

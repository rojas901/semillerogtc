package com.semillerogtc.gtcusermanagement.services;

import com.semillerogtc.gtcusermanagement.components.UsersValidation;
import com.semillerogtc.gtcusermanagement.domain.Usuario;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    UsersValidation _usersValidation;

    UsersService(UsersValidation usersValidation) {
        _usersValidation = usersValidation;
    }

    public Boolean registrarUsuario(Usuario user) {
        boolean resultado = _usersValidation.execute(user);
        return resultado;
    }

    public Boolean consultarUsuario() {
        return true;
    }
}

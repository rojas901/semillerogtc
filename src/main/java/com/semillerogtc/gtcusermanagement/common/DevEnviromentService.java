package com.semillerogtc.gtcusermanagement.common;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DevEnviromentService implements EnviromentService {
    @Override
    public String getEnviromentName() {
        return "ambiente dev";
    }

    @Override
    public String obtenerPoliticaDeClaveDeUsuario() {
        return "[0-9A-ba-z]";
    }
}

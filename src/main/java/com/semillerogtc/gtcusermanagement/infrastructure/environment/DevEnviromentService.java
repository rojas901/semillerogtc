package com.semillerogtc.gtcusermanagement.infrastructure.environment;

import org.springframework.stereotype.Service;

@Service
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

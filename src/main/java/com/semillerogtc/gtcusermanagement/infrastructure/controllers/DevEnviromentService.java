package com.semillerogtc.gtcusermanagement.infrastructure.controllers;

import com.semillerogtc.gtcusermanagement.common.EnviromentService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
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

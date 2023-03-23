package com.semillerogtc.gtcusermanagement.infrastructure.environment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:applicationprod.properties")
public class ProEnviromentService implements EnviromentService {

    @Value("${environment.name:0}")
    String nombre;

    @Value("${environment.secret:0}")
    String secreto;

    @Override
    public String getEnviromentName() {
        return nombre;
    }

    @Override
    public String getEnviromentSecret() {
        return secreto;
    }
}

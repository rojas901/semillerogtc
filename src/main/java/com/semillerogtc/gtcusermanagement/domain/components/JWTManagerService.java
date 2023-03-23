package com.semillerogtc.gtcusermanagement.domain.components;

import com.semillerogtc.gtcusermanagement.domain.Email;
import com.semillerogtc.gtcusermanagement.domain.InvalidTokenException;
import com.semillerogtc.gtcusermanagement.domain.UsuariosRepositorio;
import com.semillerogtc.gtcusermanagement.infrastructure.environment.EnviromentService;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.JwtSignatureValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTManagerService {
    @Autowired
    UsuariosRepositorio _usuarioRepositorio;

    public String generate(String email, String secret) {
        //Date expirationDate = new Date(System.currentTimeMillis() + (3600*3));
        String jwt = Jwts.builder()
                .setSubject(email)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        return jwt;
    }

    public boolean validate(String jwt, String secret) {
        String result = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();

        String tokenDB = _usuarioRepositorio.findByEmail(new Email(result)).getToken();

        if (jwt.equals(tokenDB)) {
            return true;
        } else {
            throw new InvalidTokenException();
        }

    }
}

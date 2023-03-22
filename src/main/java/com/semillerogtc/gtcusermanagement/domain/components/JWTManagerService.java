package com.semillerogtc.gtcusermanagement.domain.components;

import com.semillerogtc.gtcusermanagement.domain.Email;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.JwtSignatureValidator;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTManagerService {
    private String secret = "misecreto";

    public String generate(String email) {
        Date expirationDate = new Date(System.currentTimeMillis() + (3600*3));
        String jwt = Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, this.secret)
                .compact();
        return jwt;
    }

    public String validate(String jwt) {
        var result = Jwts.parser()
                .setSigningKey(this.secret)
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
        return result;
    }
}

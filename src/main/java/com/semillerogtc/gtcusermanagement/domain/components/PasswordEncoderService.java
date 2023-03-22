package com.semillerogtc.gtcusermanagement.domain.components;

import com.semillerogtc.gtcusermanagement.domain.InvalidPasswordException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class PasswordEncoderService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PasswordEncoderService() {this.bCryptPasswordEncoder = new BCryptPasswordEncoder();    }

    public String encode(String password) {

        final Pattern PASSWORD_FORMAT_REGEX = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,8}$");

        if (PASSWORD_FORMAT_REGEX.matcher(password).matches()) {
            return bCryptPasswordEncoder.encode(password);
        }
        else {
            throw new InvalidPasswordException();
        }
    }

    public boolean validarPassword(String password, String passwordEncriptado) {
        return bCryptPasswordEncoder.matches(password, passwordEncriptado);
    }
}

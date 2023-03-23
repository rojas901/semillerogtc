package com.semillerogtc.gtcusermanagement.domain;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class UsuarioNuevoDto {
    //solo se usa para transmitir mensajes
    @NotEmpty(message = "Nombre es obligatorio")
    private String nombre;
    @NotEmpty(message = "Email es obligatorio")
    private String email;
    @NotEmpty(message = "Password es obligatorio")
    private String password;
    private List<Telefono> telefonos;

    private Date lastAccess;

    private boolean activo;
}

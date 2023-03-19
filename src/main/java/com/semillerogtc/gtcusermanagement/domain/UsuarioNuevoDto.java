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
    private int edad;
    private List<Telefono> telefonos;
}

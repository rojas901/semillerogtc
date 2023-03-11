package com.semillerogtc.gtcusermanagement.domain;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
public class UsuarioDto {
    //solo se usa para transmitir mensajes
    private String nombre;
    @NotEmpty
    private String email;
    @NotEmpty
    private int edad;
    private long celular;
    private Date fechaNacimiento;
}

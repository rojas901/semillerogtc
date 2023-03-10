package com.semillerogtc.gtcusermanagement.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UsuarioDto2 {

    @NotEmpty(message = "El nombre no puede ser vacio")
    @NotNull
    @NotBlank
    public String nombre;

    @NotEmpty(message = "El apellido no puede ser vacio")
    @NotNull
    @NotBlank
    public String apellido;

    public String celular;
}

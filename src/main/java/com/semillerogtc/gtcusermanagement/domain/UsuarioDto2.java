package com.semillerogtc.gtcusermanagement.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UsuarioDto2 {
    @NotEmpty
    public String email;
    @NotEmpty
    public String userId;
    @NotEmpty
    public String celular;
}

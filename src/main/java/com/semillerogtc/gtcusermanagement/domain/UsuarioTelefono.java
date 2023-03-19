package com.semillerogtc.gtcusermanagement.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="usuarios_telefonos")
public class UsuarioTelefono {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Convert(converter = TelefonoAttributeConverter.class)
    private Telefono telefono;

    public List<String> getTelefono() {
        return List.of(telefono.getNumber(), telefono.getCitycode(), telefono.getCountrycode());
    }

    public void setTelefono(Telefono telefono) {
        this.telefono = new Telefono(telefono.getNumber(), telefono.getCitycode(), telefono.getCountrycode());
    }
}

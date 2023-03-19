package com.semillerogtc.gtcusermanagement.domain;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name="usuarios_telefonos")
public class UsuarioTelefono {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Convert(converter = TelefonoAttributeConverter.class)
    private Telefono telefono;

    /*public List<String> getTelefono() {
        return List.of(telefono.getNumber(), telefono.getCitycode(), telefono.getCountrycode());
    }*/

    /*public Map<String, String> getTelefono() {
        Map<String, String> phone = new HashMap<>();
        phone.put("number", telefono.getNumber());
        phone.put("citycode", telefono.getCitycode());
        phone.put("countrycode", telefono.getCountrycode());
        return phone;
    }*/

    public Telefono getTelefono() {
        return telefono;
    }

    public void setTelefono(Telefono telefono) {
        this.telefono = new Telefono(telefono.getNumber(), telefono.getCitycode(), telefono.getCountrycode());
    }
}

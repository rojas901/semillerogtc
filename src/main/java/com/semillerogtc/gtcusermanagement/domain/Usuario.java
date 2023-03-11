package com.semillerogtc.gtcusermanagement.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@NoArgsConstructor
public class Usuario {
    //es una entidad porque maneja id o indentificador
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotEmpty(message = "El parametro no es obligatorio")
    private String nombre;
    private String email;
    private Integer edad;
    private long celular;

}

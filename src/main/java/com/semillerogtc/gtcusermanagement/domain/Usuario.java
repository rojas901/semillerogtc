package com.semillerogtc.gtcusermanagement.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name="usuarios")
public class Usuario {
    //es una entidad porque maneja id o indentificador
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotEmpty(message = "El nombre es obligatorio")
    private String nombre;
    @Convert(converter = EmailAttributeConverter.class)
    private Email email;
    private String password;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    Set<UsuarioTelefono> telefonos;

    @CreationTimestamp
    @Column(name="createdAt", nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    private Date modifyAt;

    private Date lastAccess;

    private String token;

    private boolean activo;

}

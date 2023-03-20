package com.semillerogtc.gtcusermanagement.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Set;

@Data
public class UsuarioCreadoDto {
    private String id;

    private Date createdAt;

    private Date modifyAt;

    private Date lastAccess;

    private String token;

    private boolean activo;
}

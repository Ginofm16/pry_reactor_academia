package com.academia.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@NoArgsConstructor
@Document(value = "usuarios")
public class Usuario {

    @Id
    private String id;
    private String usuario;
    private String clave;
    private Boolean estado;

    private List<Rol> roles;

}

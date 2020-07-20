package com.academia.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@Document(collection = "cursos")
public class Curso {

    @Id
    private String id;

    @NotEmpty
    private String nombre;

    @NotEmpty
    private String siglas;

    private Boolean estado;

}

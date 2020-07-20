package com.academia.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@Document(collection = "estudiantes")
public class Estudiante {

    @Id
    private String id;

    @NotEmpty
    private String nombres;

    @NotEmpty
    private String apellidos;

    @NotNull(message = "Campo obligatorio")
    private String dni;

    private Integer edad;

}

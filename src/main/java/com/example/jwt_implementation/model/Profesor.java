package com.example.jwt_implementation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("profesor")
public class Profesor extends Usuario{

    @Column
    private String nombre;
    @Column
    private String email;
    @Column
    private String foto;

//    @OneToMany(mappedBy = "profesor")
//    private List<Curso> Cursos;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
//    private Usuario usuario;

    @Serial
    private static final long serialVersionUID = 1L;
}

package com.ifs_jogos.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String matricula;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String nomeCompleto;

    private String apelido;

    @Column(nullable = false)
    private String telefone;

    @Enumerated(EnumType.STRING)
    private UsuarioEnum tipoUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

}

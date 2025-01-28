package com.gerenciador.projetos.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tarefas")
@Data
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "projeto_id", nullable = false)
    private Projeto projeto;

    @Column(nullable = false)
    private String responsavel;

    @Column(name = "prazo_dias", nullable = false)
    private Integer prazoDias;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;
}


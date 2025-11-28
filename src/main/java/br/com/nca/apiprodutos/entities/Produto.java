package br.com.nca.apiprodutos.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Where(clause = " ativo = true")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(nullable = false)
    private Integer quantidade;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataHoraCriacao;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataHoraUltimaAlteracao;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataHoraExclusao;

    @ManyToOne
    @JoinColumn
    private Usuario usuarioCriacao;

    @ManyToOne
    @JoinColumn
    private Usuario usuarioAlteracao;

    @ManyToOne
    @JoinColumn
    private Usuario usuarioExclusao;

    @Column(nullable = false)
    private Boolean ativo;

    @PrePersist
    public void onCreate(){
        this.dataHoraCriacao = LocalDateTime.now();
        this.ativo = true;
    }

    public void softDelete(Usuario usuarioExclusao){
        this.dataHoraExclusao = LocalDateTime.now();
        this.ativo = false;
        this.usuarioExclusao = usuarioExclusao;
    }
}

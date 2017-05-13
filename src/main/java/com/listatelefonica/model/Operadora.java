package com.listatelefonica.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Operadora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotNull
    private Integer codigo;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @NumberFormat(pattern = "0.00")
    @NotNull
    private BigDecimal preco;

    public Operadora(String nome, Integer codigo, Categoria categoria, BigDecimal preco) {
        this.nome = nome;
        this.codigo = codigo;
        this.categoria = categoria;
        this.preco = preco;
    }

    public Operadora() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Operadora{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", codigo=" + codigo +
                ", categoria=" + categoria +
                ", preco=" + preco +
                '}';
    }
}

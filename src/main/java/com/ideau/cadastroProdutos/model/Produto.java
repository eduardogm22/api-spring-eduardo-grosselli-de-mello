package com.ideau.cadastroProdutos.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotBlank
    @Size(min=5, max=100)
    String nome;
    @Size(min=0, max=255)
    String descricao;
    @Positive
    BigDecimal valorUnitario;
    @NotNull
    Integer qtdEmEstoque;
    @JsonFormat(pattern="dd/MM/yyyy")
    LocalDate dtValidade;
}

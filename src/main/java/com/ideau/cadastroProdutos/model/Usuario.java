package com.ideau.cadastroProdutos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotBlank
    @Size(min=5, max=100)
    String username;
    @NotBlank
    @Size(min=5, max=100)
    String senha;

    public Usuario(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}

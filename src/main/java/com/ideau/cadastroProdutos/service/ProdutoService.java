package com.ideau.cadastroProdutos.service;

import com.ideau.cadastroProdutos.exception.SimplesHttpException;
import com.ideau.cadastroProdutos.model.Produto;
import com.ideau.cadastroProdutos.repository.ProdutoRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    private final ProdutoRepository repo;

    public ProdutoService(ProdutoRepository repo) {
        this.repo = repo;
    }

    public Produto cadastraProduto(Produto prod) {
            if (repo.existsByNome(prod.getNome())) {
                throw new SimplesHttpException(HttpStatus.CONFLICT, "Produto já cadastrado!");
            }
        try {
            repo.save(prod);
        } catch (ConstraintViolationException e) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<?> v : e.getConstraintViolations()) {
                sb.append("Erro no campo: ")
                        .append(v.getPropertyPath())
                        .append(" - Erro: ")
                        .append(v.getMessage());
            }
            throw new SimplesHttpException(HttpStatus.BAD_REQUEST, sb.toString());
        }
        return prod;
    }
    public List<Produto> retornaTodosProdutos() {
        return repo.findAll();
    }
    public Produto retornaPorId(Long id) {
        Optional<Produto> optional = repo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new SimplesHttpException(HttpStatus.NOT_FOUND, "Não encontrado nenhum produto com id: " + id);
        }
    }
    public Produto atualizaProduto(Produto prod) {
        if (prod.getId() == null) {
            throw new SimplesHttpException(HttpStatus.BAD_REQUEST, "Informe o id do produto que deseja atualizar!");
        }
        if (!repo.existsById(prod.getId())) {
            throw new SimplesHttpException(HttpStatus.NOT_FOUND, "Não encontrado nenhum produto com id: " + prod.getId());
        }
        repo.save(prod);
        return prod;
    }
    public HttpStatus deletaProduto(Long id) {
        if (!repo.existsById(id)) {
            throw new SimplesHttpException(HttpStatus.NOT_FOUND, "Não encontrado nenhum produto com id: " + id);
        }
        repo.deleteAllById(Arrays.asList(id));
        return HttpStatus.NO_CONTENT;
    }
}

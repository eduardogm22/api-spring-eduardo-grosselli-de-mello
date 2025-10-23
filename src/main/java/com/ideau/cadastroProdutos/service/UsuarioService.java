package com.ideau.cadastroProdutos.service;

import com.ideau.cadastroProdutos.exception.SimplesHttpException;
import com.ideau.cadastroProdutos.model.Produto;
import com.ideau.cadastroProdutos.model.Usuario;
import com.ideau.cadastroProdutos.model.UsuarioDTO;
import com.ideau.cadastroProdutos.repository.ProdutoRepository;
import com.ideau.cadastroProdutos.repository.UsuarioRepository;
import jakarta.persistence.RollbackException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioDTO cadastraUsuario(Usuario user) {
        if (repo.existsByUsername(user.getUsername())) {
            throw new SimplesHttpException(HttpStatus.CONFLICT, "Nome de usuário já em uso!");
        }
        if (user.getSenha().length() < 5 || user.getSenha().length() > 100) {
            throw new SimplesHttpException(HttpStatus.BAD_REQUEST, "Erro no campo: senha - Erro: tamanho deve ser entre 5 e 100");
        } else {
            user.setSenha(passwordEncoder.encode(user.getSenha()));
        }
        try {
            repo.save(user);
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
        return new UsuarioDTO(user.getId(), user.getUsername());
    }
    public List<UsuarioDTO> retornaTodosUsuarios() {
        List<Usuario> lstUsuario= repo.findAll();
        if (lstUsuario.isEmpty()) {
            throw new SimplesHttpException(HttpStatus.NOT_FOUND, "Nenhum usuário cadastrado no sistema.");
        }
        return lstUsuario.stream()
                .map(user -> new UsuarioDTO(user.getId(), user.getUsername()))
                .toList();
    }
    public UsuarioDTO retornaPorId(Long id) {
        Optional<Usuario> optional = repo.findById(id);
        if (optional.isPresent()) {
            return new UsuarioDTO(optional.get().getId(), optional.get().getUsername());
        } else {
            throw new SimplesHttpException(HttpStatus.NOT_FOUND, "Não encontrados usuários com id: " + id);
        }
    }
    public UsuarioDTO atualizaUsuario(Usuario user) {
        if (user.getId() == null) {
            throw new SimplesHttpException(HttpStatus.BAD_REQUEST, "Informe o id do usuário que deseja atualizar!");
        }
        if (repo.existsByUsername(user.getUsername())) {
            throw new SimplesHttpException(HttpStatus.CONFLICT, "Nome de usuário já em uso!");
        }
        if (user.getSenha().length() < 5 || user.getSenha().length() > 100) {
            throw new SimplesHttpException(HttpStatus.BAD_REQUEST, "Erro no campo: senha - Erro: tamanho deve ser entre 5 e 100");
        } else {
            user.setSenha(passwordEncoder.encode(user.getSenha()));
        }
        try {
            repo.save(user);
        } catch (TransactionSystemException e) {
            if (e.getCause() instanceof RollbackException rollbackEx &&
                    rollbackEx.getCause() instanceof ConstraintViolationException cve) {
                String msg = "";
                for (ConstraintViolation<?> v : cve.getConstraintViolations()) {
                    msg = "Erro no campo: " + v.getPropertyPath() + " - Erro: " + v.getMessage();
                }
                throw new SimplesHttpException(HttpStatus.BAD_REQUEST, msg);
            }
            throw e; // se não for validação, relança
        }
        return new UsuarioDTO(user.getId(), user.getUsername());
    }
    public HttpStatus deletaUsuario(Long id) {
        if (!repo.existsById(id)) {
            throw new SimplesHttpException(HttpStatus.NOT_FOUND, "Não encontrado usuário com id: " + id);
        }
        repo.deleteAllById(Arrays.asList(id));
        return HttpStatus.NO_CONTENT;
    }
}

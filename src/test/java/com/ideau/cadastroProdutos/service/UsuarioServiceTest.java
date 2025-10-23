package com.ideau.cadastroProdutos.service;

import com.ideau.cadastroProdutos.exception.SimplesHttpException;
import com.ideau.cadastroProdutos.model.Produto;
import com.ideau.cadastroProdutos.model.Usuario;
import com.ideau.cadastroProdutos.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService svc;

    @Test
    void deveSalvarUsuario() {
        Usuario user = new Usuario();
        user.setUsername("usuarioTeste");
        user.setSenha("senhaTeste");

        Usuario userSalvo = svc.cadastraUsuario(user);

        assertNotNull(userSalvo.getId());
        assertEquals("usuarioTeste", userSalvo.getUsername());
        assertEquals(null, userSalvo.getSenha()); //Não deve retornar a senha
    }


    @Test
    void deveRetornarConflito() {
        UsuarioRepository repo = mock(UsuarioRepository.class);
        PasswordEncoder passEncod = mock(PasswordEncoder.class);
        UsuarioService svc = new UsuarioService(repo, passEncod);

        Usuario user = new Usuario();
        user.setUsername("usuarioTeste");
        user.setSenha("senhaTeste");

        when(repo.existsByUsername(user.getUsername())).thenReturn(true);

        SimplesHttpException e = assertThrows(
                SimplesHttpException.class,
                () -> svc.cadastraUsuario(user)
        );

        assertEquals(HttpStatus.CONFLICT, e.getHttpStatus());
        assertEquals("Nome de usuário já em uso!", e.getMessage());

        verify(repo, never()).save(any(Usuario.class));
    }
}

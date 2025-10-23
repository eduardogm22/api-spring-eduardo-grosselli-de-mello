package com.ideau.cadastroProdutos.service;

import com.ideau.cadastroProdutos.exception.SimplesHttpException;
import com.ideau.cadastroProdutos.model.Produto;
import com.ideau.cadastroProdutos.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class ProdutoServiceTest {

    @Autowired
    private ProdutoService svc;

    @Test
    void deveSalvarProdutoTodosAtributos() {
        Produto prod = new Produto();
        prod.setNome("Leite UHT Semi-desnatado Tirol 1L");
        prod.setDescricao("Leite longa vida em embalagem de um litro");
        prod.setValorUnitario(BigDecimal.valueOf(3.45));
        prod.setQtdEmEstoque(12);
        prod.setDtValidade(LocalDate.of(2025, 10, 21));
        Produto prodSalvo = svc.cadastraProduto(prod);

        assertNotNull(prodSalvo.getId());
        assertEquals("Leite UHT Semi-desnatado Tirol 1L", prodSalvo.getNome());
        assertEquals("Leite longa vida em embalagem de um litro", prodSalvo.getDescricao());
        assertEquals(BigDecimal.valueOf(3.45), prodSalvo.getValorUnitario());
        assertEquals(12, prodSalvo.getQtdEmEstoque());
        assertEquals(LocalDate.of(2025, 10, 21), prodSalvo.getDtValidade());
    }

    @Test
    void deveSalvarProdutoSemValidade() {
        Produto prod = new Produto();
        prod.setNome("Notebook Acer Nitro 5");
        prod.setDescricao("Notebook com Intel Core I5 e GTX 1650");
        prod.setValorUnitario(BigDecimal.valueOf(3000.45));
        prod.setQtdEmEstoque(5);
        //sem data de validade, obviamente

        Produto prodSalvo = svc.cadastraProduto(prod);

        assertNotNull(prodSalvo.getId());
        assertEquals("Notebook Acer Nitro 5", prodSalvo.getNome());
        assertEquals("Notebook com Intel Core I5 e GTX 1650", prodSalvo.getDescricao());
        assertEquals(BigDecimal.valueOf(3000.45), prodSalvo.getValorUnitario());
        assertEquals(5, prodSalvo.getQtdEmEstoque());
    }

    @Test
    void deveLancarConflitoQuandoProdutoJaExiste() {

        ProdutoRepository repo = mock(ProdutoRepository.class);
        ProdutoService svc = new ProdutoService(repo);

        Produto prod = new Produto();
        prod.setNome("Notebook Acer Nitro 5");
        prod.setValorUnitario(BigDecimal.valueOf(3000.45));
        prod.setQtdEmEstoque(5);

        when(repo.existsByNome("Notebook Acer Nitro 5")).thenReturn(true);

        SimplesHttpException e = assertThrows(
                SimplesHttpException.class,
                () -> svc.cadastraProduto(prod)
        );

        assertEquals(HttpStatus.CONFLICT, e.getHttpStatus());
        assertEquals("Produto já cadastrado!", e.getMessage());

        verify(repo, never()).save(any(Produto.class));
    }
}

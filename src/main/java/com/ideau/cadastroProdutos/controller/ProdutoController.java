package com.ideau.cadastroProdutos.controller;

import com.ideau.cadastroProdutos.model.Produto;
import com.ideau.cadastroProdutos.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {
    private final ProdutoService svc;

    public ProdutoController(ProdutoService svc) {
        this.svc = svc;
    }

    @PostMapping
    public Produto postProduto(@RequestBody Produto prod) {
        return svc.cadastraProduto(prod);
    }
    @GetMapping
    public List<Produto> getProdutos() {
        return svc.retornaTodosProdutos();
    }
    @GetMapping(params = "id")
    public Produto getProdutoById(@RequestParam Long id) {
        return svc.retornaPorId(id);
    }
    @PutMapping
    public Produto putProduto(@RequestBody Produto prod) {
        return svc.atualizaProduto(prod);
    }
    @DeleteMapping(params = "id")
    public HttpStatus deleteProduto(@RequestParam Long id) {
        return svc.deletaProduto(id);
    }
}

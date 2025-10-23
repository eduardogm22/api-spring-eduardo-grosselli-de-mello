package com.ideau.cadastroProdutos.controller;

import com.ideau.cadastroProdutos.model.Produto;
import com.ideau.cadastroProdutos.model.Usuario;
import com.ideau.cadastroProdutos.model.UsuarioDTO;
import com.ideau.cadastroProdutos.service.ProdutoService;
import com.ideau.cadastroProdutos.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioService svc;

    public UsuarioController(UsuarioService svc) {
        this.svc = svc;
    }

    @PostMapping("/cadastro")
    public UsuarioDTO postUsuario(@RequestBody Usuario user) {
        return svc.cadastraUsuario(user);
    }
    @GetMapping
    public List<UsuarioDTO> getUsuarios() {
        return svc.retornaTodosUsuarios();
    }
    @GetMapping(params = "id")
    public UsuarioDTO getUsuarioById(@RequestParam Long id) {
        return svc.retornaPorId(id);
    }
    @PutMapping
    public UsuarioDTO putUsuario(@RequestBody Usuario user) {
        return svc.atualizaUsuario(user);
    }
    @DeleteMapping(params = "id")
    public HttpStatus deleteUsuario(@RequestParam Long id) {
        return svc.deletaUsuario(id);
    }
}

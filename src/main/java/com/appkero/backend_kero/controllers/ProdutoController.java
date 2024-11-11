package com.appkero.backend_kero.controllers;

import com.appkero.backend_kero.domain.produto.Produto;
import com.appkero.backend_kero.domain.produto.ProdutoRequest;
import com.appkero.backend_kero.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/new/{usuarioId}")
    public ResponseEntity<Produto> insert(@RequestBody ProdutoRequest produtoRequest, @PathVariable Long usuarioId) {
        Produto produto = produtoService.cadastrarProduto(produtoRequest, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }

    @GetMapping("/tag/{nomeTag}")
    public ResponseEntity<List<Produto>> buscarPorTag(@PathVariable String nomeTag) {
        List<Produto> produtos = produtoService.buscarPorTags(nomeTag);
        return ResponseEntity.ok(produtos);
    }

}

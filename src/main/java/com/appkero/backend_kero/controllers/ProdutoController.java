package com.appkero.backend_kero.controllers;

import com.appkero.backend_kero.domain.produto.Produto;
import com.appkero.backend_kero.domain.produto.ProdutoRequest;
import com.appkero.backend_kero.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping(value = "/new/{usuarioId}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> insert(
            @RequestPart("produto") ProdutoRequest produtoRequest,
            @RequestPart("files") List<MultipartFile> files,
            @PathVariable Long usuarioId
    ) {
        try {
            Produto produto = produtoService.cadastrarProduto(produtoRequest, files, usuarioId);
            return ResponseEntity.status(HttpStatus.CREATED).body(produto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao adicionar produto: " + e.getMessage());
        }
    }

    @PatchMapping("/atulizar-produto/{produtoId}")
    public ResponseEntity<?> atualizarProduto(@PathVariable Long produtoId, @RequestBody ProdutoRequest produtoRequest) {
        try {
            Produto produto = produtoService.atualizarProduto(produtoRequest, produtoId);
            return ResponseEntity.status(HttpStatus.OK).body(produto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar produto.");
        }
    }

    @DeleteMapping("/remover/{produtoId}")
    public ResponseEntity<?> remover(@PathVariable Long produtoId) {
        try {
            produtoService.deleteProduto(produtoId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Produto removido.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao remover produto.");
        }
    }

    @GetMapping("/{nomeProduto}")
    public ResponseEntity<?> buscarPorNome(@PathVariable String nomeProduto) {
        try {
            List<Produto> produtos = produtoService.buscarProdutosPorNome(nomeProduto);
            return ResponseEntity.status(HttpStatus.OK).body(produtos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar produtos.");
        }
    }

    @GetMapping
    public ResponseEntity<?> buscarTodos() {
        try {
            List<Produto> produtos = produtoService.findAllProdutos();
            return ResponseEntity.status(HttpStatus.OK).body(produtos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar produtos.");
        }
    }

    @GetMapping("/tag/{nomeTag}")
    public ResponseEntity<?> buscarPorTag(@PathVariable String nomeTag) {
        try {
            List<Produto> produtos = produtoService.buscarPorTags(nomeTag);
            return ResponseEntity.status(HttpStatus.OK).body(produtos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar produtos.");
        }
    }

}

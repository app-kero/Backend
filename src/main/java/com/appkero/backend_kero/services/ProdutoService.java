package com.appkero.backend_kero.services;

import com.appkero.backend_kero.domain.produto.Tag;
import com.appkero.backend_kero.repositories.TagRepository;
import org.springframework.stereotype.Service;

import com.appkero.backend_kero.domain.produto.Produto;
import com.appkero.backend_kero.domain.produto.ProdutoRequest;
import com.appkero.backend_kero.domain.redeSocial.RedeSocial;
import com.appkero.backend_kero.domain.usuario.Usuario;
import com.appkero.backend_kero.repositories.ProdutoRepository;
import com.appkero.backend_kero.repositories.RedeSocialRepository;
import com.appkero.backend_kero.repositories.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final TagRepository tagRepository;
    private final UsuarioRepository usuarioRepository;

    public ProdutoService(ProdutoRepository produtoRepository, TagRepository tagRepository, UsuarioRepository usuarioRepository) {
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
        this.tagRepository = tagRepository;
    }

    public Produto cadastrarProduto(ProdutoRequest produto, Long usuarioId) {
        Usuario who = this.usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        List<Tag> tags = produto.tags().stream()
                .map(tagName -> tagRepository.findByNome(tagName)
                        .orElseGet(() -> {
                            Tag newTag = Tag.builder().nome(tagName).build();
                            return tagRepository.save(newTag);
                        })).toList();

        Produto produtoDB = Produto.builder()
                .nome(produto.nome())
                .descricao(produto.descricao())
                .preco(produto.preco())
                .horario(produto.horario())
                .local(produto.local())
                .usuario(who)
                .tags(tags)
                .build();

        return this.produtoRepository.save(produtoDB);
    }

    public Produto atualizarProduto(ProdutoRequest produto, Long produtoId) {
        Produto produtoDb = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));

        if (produto.nome() != null) {
            produtoDb.setNome(produto.nome());
        }
        if (produto.descricao() != null) {
            produtoDb.setDescricao(produto.descricao());
        }
        if (produto.preco() != null) {
            produtoDb.setPreco(produto.preco());
        }
        if (produto.horario() != null) {
            produtoDb.setHorario(produto.horario());
        }
        if (produto.local() != null) {
            produtoDb.setLocal(produto.local());
        }

        List<Tag> tags = produto.tags().stream()
                .map(tagName -> tagRepository.findByNome(tagName)
                        .orElseGet(() -> {
                            Tag newTag = Tag.builder().nome(tagName).build();
                            return tagRepository.save(newTag);
                        })).toList();

        produtoDb.setTags(tags);

        return this.produtoRepository.save(produtoDb);
    }

    public void deleteProduto(Long produtoId) {
        this.produtoRepository.deleteById(produtoId);
    }

    public List<Produto> buscarProdutosPorNome(String nome) {
        return produtoRepository.findProdutosByNomeContainsIgnoreCase(nome);
    }

    public List<Produto> buscarPorTags(String tagNome) {
        return produtoRepository.findByTagsNomeIgnoreCase(tagNome);
    }
    
}

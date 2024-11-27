package com.appkero.backend_kero.services;

import com.appkero.backend_kero.domain.arquivo.Arquivo;
import com.appkero.backend_kero.domain.produto.Tag;
import com.appkero.backend_kero.repositories.TagRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.appkero.backend_kero.domain.produto.Produto;
import com.appkero.backend_kero.domain.produto.ProdutoRequest;
import com.appkero.backend_kero.domain.redeSocial.RedeSocial;
import com.appkero.backend_kero.domain.usuario.Usuario;
import com.appkero.backend_kero.repositories.ProdutoRepository;
import com.appkero.backend_kero.repositories.RedeSocialRepository;
import com.appkero.backend_kero.repositories.UsuarioRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final TagRepository tagRepository;
    private final UsuarioRepository usuarioRepository;
    private final ArquivoService arquivoService;

    public ProdutoService(ProdutoRepository produtoRepository, TagRepository tagRepository, UsuarioRepository usuarioRepository, ArquivoService arquivoService) {
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
        this.tagRepository = tagRepository;
        this.arquivoService = arquivoService;
    }

    @Transactional
    public Produto cadastrarProduto(ProdutoRequest produto, List<MultipartFile> files, Long usuarioId) throws Exception {
        Usuario who = this.usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        List<Tag> existingTags = tagRepository.findByNomeIn(produto.tags());
        List<Tag> newTags = produto.tags().stream()
                .filter(tagName -> existingTags.stream().noneMatch(tag -> tag.getNome().equals(tagName)))
                .map(tagName -> Tag.builder().nome(tagName).build())
                .collect(Collectors.toList());

        if (!newTags.isEmpty()) {
            tagRepository.saveAll(newTags);
        }

        List<Tag> allTags = Stream.concat(existingTags.stream(), newTags.stream()).collect(Collectors.toList());

        Produto produtoDB = Produto.builder()
                .nome(produto.nome())
                .descricao(produto.descricao())
                .preco(produto.preco())
                .horario(produto.horario())
                .local(produto.local())
                .fotos(new ArrayList<>())
                .usuario(who)
                .tags(allTags)
                .build();

        produtoDB = this.produtoRepository.save(produtoDB);

        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                Arquivo arquivo = arquivoService.store(file);
                produtoDB.getFotos().add(arquivo);
            }
        }

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

    public List<Produto> findAllProdutos() {
        return this.produtoRepository.findAll();
    }
    
}

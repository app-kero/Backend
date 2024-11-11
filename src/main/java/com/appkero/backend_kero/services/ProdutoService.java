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
                .horario(produto.horario())
                .local(produto.local())
                .usuario(who)
                .tags(tags)
                .build();

        return this.produtoRepository.save(produtoDB);
    }

    public List<Produto> buscarPorTags(String tagNome) {
        return produtoRepository.findByTagsNome(tagNome);
    }
    
}

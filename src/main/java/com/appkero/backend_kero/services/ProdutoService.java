package com.appkero.backend_kero.services;

import org.springframework.stereotype.Service;

import com.appkero.backend_kero.entities.Produto;
import com.appkero.backend_kero.entities.RedeSocial;
import com.appkero.backend_kero.entities.Usuario;
import com.appkero.backend_kero.entities.DTOs.ProdutoRequest;
import com.appkero.backend_kero.repositories.ProdutoRepository;
import com.appkero.backend_kero.repositories.RedeSocialRepository;
import com.appkero.backend_kero.repositories.UsuarioRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final RedeSocialRepository redeSocialRepository;
    private final UsuarioRepository usuarioRepository;

    public ProdutoService(ProdutoRepository produtoRepository, RedeSocialRepository redeSocialRepository, UsuarioRepository usuarioRepository) {
        this.produtoRepository = produtoRepository;
        this.redeSocialRepository = redeSocialRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Produto cadastrarProduto(ProdutoRequest produto) {
        Usuario who = this.usuarioRepository.findById(produto.usuarioId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        RedeSocial redeSocial = new RedeSocial();
        redeSocial.setWhatsapp(produto.redeSocial().whatsapp());
        redeSocial.setFacebook(produto.redeSocial().facebook());
        redeSocial.setSite(produto.redeSocial().site());
        redeSocial.setInstagram(produto.redeSocial().instagram());

        redeSocial = redeSocialRepository.save(redeSocial);

        Produto produtoDB = new Produto();
        produtoDB.setNome(produto.nome());
        produtoDB.setDescricao(produto.descricao());
        produtoDB.setHorario(produto.horario());
        produtoDB.setLocal(produto.local());
        produtoDB.setUsuario(who);
        produtoDB.setRedeSocial(redeSocial);

        return this.produtoRepository.save(produtoDB);
    }
    
}

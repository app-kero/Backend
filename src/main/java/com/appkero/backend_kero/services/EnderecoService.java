package com.appkero.backend_kero.services;

import org.springframework.stereotype.Service;

import com.appkero.backend_kero.domain.endereco.Endereco;
import com.appkero.backend_kero.domain.endereco.EnderecoRequest;
import com.appkero.backend_kero.repositories.EnderecoRepository;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public Endereco insert(EnderecoRequest endereco) {
        Endereco enderecoDB = new Endereco();
        enderecoDB.setRua(endereco.rua());
        enderecoDB.setBairro(endereco.bairro());
        enderecoDB.setNumero(endereco.numero());
        enderecoDB.setCidade(endereco.cidade());
        enderecoDB.setEstado(endereco.estado());
        return enderecoRepository.save(enderecoDB);
    }
    
}

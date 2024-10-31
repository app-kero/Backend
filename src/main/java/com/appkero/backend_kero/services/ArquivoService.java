package com.appkero.backend_kero.services;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.appkero.backend_kero.entities.Arquivo;
import com.appkero.backend_kero.repositories.ArquivoRepository;

@Service
public class ArquivoService {

    private final ArquivoRepository arquivoRepository;
    
    public ArquivoService(ArquivoRepository arquivoRepository) {
        this.arquivoRepository = arquivoRepository;
    }

    public Arquivo store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Arquivo arquivo = Arquivo.builder()
            .name(fileName)
            .contentType(file.getContentType()) 
            .data(file.getBytes())
            .size(file.getSize())
            .build();

        return arquivoRepository.save(arquivo);
    }

    public Arquivo getArquivo(Long id) throws FileNotFoundException {
        return arquivoRepository.findById(id)
            .orElseThrow(() -> new FileNotFoundException("Arquivo não encontrado com o id " + id));
    }

    public Stream<Arquivo> getArquivos() {
        return arquivoRepository.findAll().stream();
    }
}

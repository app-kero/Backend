package com.appkero.backend_kero.services;

import java.io.InputStream;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.appkero.backend_kero.domain.arquivo.Arquivo;
import com.appkero.backend_kero.repositories.ArquivoRepository;

@Slf4j
@Service
public class ArquivoService {

    @Autowired
    private ArquivoRepository arquivoRepository;

    private final MinioService minioService;

    public ArquivoService(MinioService minioService) {
        this.minioService = minioService;
    }

    public Arquivo store(MultipartFile file) throws Exception {
        String nomeArquivoS3 = minioService.uploadFile(file);

        Arquivo arquivo = Arquivo.builder()
            .name(file.getOriginalFilename())
            .contentType(file.getContentType()) 
            .size(file.getSize())
            .urlS3(nomeArquivoS3)
            .build();

        return arquivoRepository.save(arquivo);
    }

    public InputStream getArquivo(Long arquivoId) throws Exception {
        var arquivo = arquivoRepository.findById(arquivoId).orElseThrow(() -> new RuntimeException("Arquivo não existe!"));
        return minioService.downloadFile(arquivo.getUrlS3());
    }

    public Stream<Arquivo> getArquivos() {
        return arquivoRepository.findAll().stream();
    }
}

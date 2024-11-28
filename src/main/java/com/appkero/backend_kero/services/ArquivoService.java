package com.appkero.backend_kero.services;

import com.appkero.backend_kero.domain.arquivo.Arquivo;
import com.appkero.backend_kero.infra.FileStorageProperties;
import com.appkero.backend_kero.repositories.ArquivoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ArquivoService {

    @Autowired
    private ArquivoRepository arquivoRepository;

    private final Path fileStorageLocationPath;

    public ArquivoService(FileStorageProperties fileStorageProperties) {
        fileStorageLocationPath = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocationPath);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar diretório de upload: " + e.getMessage());
        }
    }

    public Arquivo uploadFile(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        String fileHash = calculateFileHash(file);

        Optional<Arquivo> existingFile = arquivoRepository.findByHash(fileHash);
        if (existingFile.isPresent()) {
            return existingFile.get();
        }

        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        Path targetLocation = fileStorageLocationPath.resolve(fileName);

        String downloadUrl = getFileDownloadUrl(fileName);

        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        Arquivo arquivo = Arquivo.builder()
                .name(fileName)
                .url(downloadUrl)
                .contentType(file.getContentType())
                .size(file.getSize())
                .hash(fileHash)
                .build();
        return arquivoRepository.save(arquivo);
    }

    private String getFileDownloadUrl(String fileName) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().replacePath(null).toUriString() + "/api/files/" + fileName;
    }

    private String calculateFileHash(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(file.getBytes());
        return HexFormat.of().formatHex(hash);
    }
}

package com.appkero.backend_kero.utils;

import java.text.Normalizer;

public class CommonsUtils {

    /**
     * Normaliza o nome de um arquivo removendo caracteres especiais,
     * substituindo espaços por underscores e convertendo para letras minúsculas.
     *
     * @param fileName o nome do arquivo a ser normalizado.
     * @return o nome do arquivo normalizado.
     */
    public static String normalizeFileName(String fileName) {
        if (fileName == null) {
            return null;
        }

        // Remove acentuações
        String normalized = Normalizer.normalize(fileName, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");

        // Remove caracteres especiais e substitui espaços por underscores
        normalized = normalized.replaceAll("[^a-zA-Z0-9\\.]", "_");

        // Remove underscores repetidos e converte para minúsculas
        normalized = normalized.replaceAll("_+", "_").toLowerCase();

        return normalized;
    }

}

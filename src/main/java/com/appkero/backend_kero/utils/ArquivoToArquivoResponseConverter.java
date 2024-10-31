package com.appkero.backend_kero.utils;

import java.io.IOException;

import com.appkero.backend_kero.entities.Arquivo;
import com.appkero.backend_kero.entities.DTOs.ArquivoResponse;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ArquivoToArquivoResponseConverter extends JsonSerializer<Arquivo>{

    @Override
    public void serialize(Arquivo value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            ArquivoResponse response = ArquivoResponse.builder()
                .id(value.getId())
                .name(value.getName())
                .url("http://localhost:8080/api/usuario/foto-perfil/"+value.getId())
                .type(value.getContentType())
                .size(value.getSize())
                .build();
            gen.writeObject(response);    
        } else {
            gen.writeNull();
        }
    }
    
}

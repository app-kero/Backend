package com.appkero.backend_kero.domain.arquivo;

import lombok.Builder;

@Builder
public record ArquivoResponse(
    Long id,
    String name,
    String url,
    String type,
    Long size
) {

}

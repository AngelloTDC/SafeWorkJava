package br.com.safework.dto;

import java.time.LocalDateTime;

public class AnaliseEpiResponseDTO {

    private Long id;
    private String imageUrl;
    private String resultadoDeteccao;
    private Double confianca;
    private String recomendacaoIa;
    private LocalDateTime dataCriacao;

    public AnaliseEpiResponseDTO() {
    }

    public AnaliseEpiResponseDTO(Long id, String imageUrl, String resultadoDeteccao,
                                 Double confianca, String recomendacaoIa, LocalDateTime dataCriacao) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.resultadoDeteccao = resultadoDeteccao;
        this.confianca = confianca;
        this.recomendacaoIa = recomendacaoIa;
        this.dataCriacao = dataCriacao;
    }

    public Long getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getResultadoDeteccao() {
        return resultadoDeteccao;
    }

    public Double getConfianca() {
        return confianca;
    }

    public String getRecomendacaoIa() {
        return recomendacaoIa;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
}

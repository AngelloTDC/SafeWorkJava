package br.com.safework.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "analise_epi")
public class AnaliseEpi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "resultado_deteccao", columnDefinition = "TEXT", nullable = false)
    private String resultadoDeteccao;

    @Column(nullable = false)
    private Double confianca;

    @Column(name = "recomendacao_ia", columnDefinition = "TEXT", nullable = false)
    private String recomendacaoIa;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    public AnaliseEpi() {}

    public AnaliseEpi(String imageUrl, String resultadoDeteccao, Double confianca, String recomendacaoIa) {
        this.imageUrl = imageUrl;
        this.resultadoDeteccao = resultadoDeteccao;
        this.confianca = confianca;
        this.recomendacaoIa = recomendacaoIa;
        this.dataCriacao = LocalDateTime.now();
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getResultadoDeteccao() {
        return resultadoDeteccao;
    }

    public void setResultadoDeteccao(String resultadoDeteccao) {
        this.resultadoDeteccao = resultadoDeteccao;
    }

    public Double getConfianca() {
        return confianca;
    }

    public void setConfianca(Double confianca) {
        this.confianca = confianca;
    }

    public String getRecomendacaoIa() {
        return recomendacaoIa;
    }

    public void setRecomendacaoIa(String recomendacaoIa) {
        this.recomendacaoIa = recomendacaoIa;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}

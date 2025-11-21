package br.com.safework.dto;

public class AnaliseEpiCreateDTO {

    private String imageUrl;
    private String resultadoDeteccao;
    private Double confianca;
    private String recomendacaoIa;

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
}

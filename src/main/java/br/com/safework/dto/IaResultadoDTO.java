package br.com.safework.dto;

public class IaResultadoDTO {
    private String resultado_deteccao;
    private double confianca;
    private String recomendacao_ia;

    public String getResultado_deteccao() {
        return resultado_deteccao;
    }

    public void setResultado_deteccao(String resultado_deteccao) {
        this.resultado_deteccao = resultado_deteccao;
    }

    public double getConfianca() {
        return confianca;
    }

    public void setConfianca(double confianca) {
        this.confianca = confianca;
    }

    public String getRecomendacao_ia() {
        return recomendacao_ia;
    }

    public void setRecomendacao_ia(String recomendacao_ia) {
        this.recomendacao_ia = recomendacao_ia;
    }
}

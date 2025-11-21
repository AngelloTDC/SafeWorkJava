package br.com.safework.service;

import br.com.safework.ai.OllamaImageAnalyzer;
import br.com.safework.dto.AnaliseEpiRequestDTO;
import br.com.safework.dto.AnaliseEpiResponseDTO;
import br.com.safework.dto.IaResultadoDTO;
import br.com.safework.model.AnaliseEpi;
import br.com.safework.repository.AnaliseEpiRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.UUID;

@Service
public class AnaliseEpiService {

    private final AnaliseEpiRepository repository;
    private final OllamaImageAnalyzer imageAnalyzer;

    public AnaliseEpiService(AnaliseEpiRepository repository,
                             OllamaImageAnalyzer imageAnalyzer) {
        this.repository = repository;
        this.imageAnalyzer = imageAnalyzer;
    }

    public AnaliseEpiResponseDTO analisarImagem(AnaliseEpiRequestDTO request) {

        String jsonIa = imageAnalyzer.analisarImagem(request.getImageUrl());

        ObjectMapper mapper = new ObjectMapper();
        IaResultadoDTO ia;

        try {
            ia = mapper.readValue(jsonIa, IaResultadoDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter resposta da IA para JSON: " + jsonIa, e);
        }

        AnaliseEpi analise = new AnaliseEpi(
                request.getImageUrl(),
                ia.getResultado_deteccao(),
                ia.getConfianca(),
                ia.getRecomendacao_ia()
        );

        AnaliseEpi salvo = repository.save(analise);

        return new AnaliseEpiResponseDTO(
                salvo.getId(),
                salvo.getImageUrl(),
                salvo.getResultadoDeteccao(),
                salvo.getConfianca(),
                salvo.getRecomendacaoIa(),
                salvo.getDataCriacao()
        );
    }

    public AnaliseEpiResponseDTO analisarImagemLocal(MultipartFile file) {

        try {
            Path uploadDir = Paths.get("uploads");
            Files.createDirectories(uploadDir);

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path destino = uploadDir.resolve(filename);

            Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

            byte[] bytes = Files.readAllBytes(destino);

            String base64 = Base64.getEncoder().encodeToString(bytes);

            String jsonIa = imageAnalyzer.analisarBase64(base64);

            ObjectMapper mapper = new ObjectMapper();
            IaResultadoDTO ia;

            try {
                ia = mapper.readValue(jsonIa, IaResultadoDTO.class);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao converter resposta da IA para JSON: " + jsonIa, e);
            }

            AnaliseEpi analise = new AnaliseEpi(
                    destino.toString(),
                    ia.getResultado_deteccao(),
                    ia.getConfianca(),
                    ia.getRecomendacao_ia()
            );

            AnaliseEpi salvo = repository.save(analise);

            return new AnaliseEpiResponseDTO(
                    salvo.getId(),
                    salvo.getImageUrl(),
                    salvo.getResultadoDeteccao(),
                    salvo.getConfianca(),
                    salvo.getRecomendacaoIa(),
                    salvo.getDataCriacao()
            );

        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar imagem local: " + e.getMessage(), e);
        }
    }
}

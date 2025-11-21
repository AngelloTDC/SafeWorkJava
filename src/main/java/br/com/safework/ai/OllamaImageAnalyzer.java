package br.com.safework.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import java.util.Map;

@Component
public class OllamaImageAnalyzer {

    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";

    private final RestTemplate rest = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public String analisarImagem(String imageUrl) {
        try {
            InputStream in = new URL(imageUrl).openStream();
            byte[] bytes = in.readAllBytes();
            in.close();

            String base64 = Base64.getEncoder().encodeToString(bytes);

            return analisarBase64(base64);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao baixar/convertar imagem: " + e.getMessage(), e);
        }
    }

    public String analisarBase64(String base64Image) {

        try {
            String prompt = """
                Você é uma IA especialista em segurança do trabalho.

                Analise a imagem enviada em base64 e gere APENAS um JSON:
                {
                  "resultado_deteccao": "texto",
                  "confianca": 0.0 a 1.0,
                  "recomendacao_ia": "texto"
                }

                Não escreva nada fora do JSON.
                """;

            Map<String, Object> body = Map.of(
                    "model", "llava",
                    "prompt", prompt,
                    "images", new String[]{ base64Image },
                    "stream", false
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity =
                    new HttpEntity<>(body, headers);

            ResponseEntity<String> response =
                    rest.exchange(OLLAMA_URL, HttpMethod.POST, entity, String.class);

            Map<String, Object> json = mapper.readValue(response.getBody(), Map.class);

            return (String) json.get("response");

        } catch (Exception e) {
            throw new RuntimeException("Erro ao chamar Ollama: " + e.getMessage(), e);
        }
    }
}

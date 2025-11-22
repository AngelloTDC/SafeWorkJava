package br.com.safework.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@Service
public class AiService {

    private final ChatClient chatClient;

    // construtor explícito (injeção de dependência)
    public AiService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String analyze(String textPrompt, MultipartFile imageFile) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Você é um especialista em segurança do trabalho. ");
        prompt.append("Analise a situação e responda em português de forma objetiva com: ");
        prompt.append("1) o que está acontecendo; 2) risco; 3) EPI faltando; 4) recomendação. ");
        prompt.append("\n\nDescrição: ").append(textPrompt == null ? "" : textPrompt);

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String b64 = Base64.getEncoder().encodeToString(imageFile.getBytes());
                prompt.append("\n\nImagem em base64 (use como referência visual): ").append(b64);
            } catch (Exception ignored) { }
        }

        Prompt p = new Prompt(new UserMessage(prompt.toString()));
        return chatClient.prompt(p).call().content();
    }
}

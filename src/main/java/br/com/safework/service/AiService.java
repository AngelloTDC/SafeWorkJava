package br.com.safework.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final ChatClient chatClient;

    public AiService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String analyze(String textPrompt) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Você é um especialista em segurança do trabalho. ");
        prompt.append("Analise a situação e responda em português de forma objetiva com: ");
        prompt.append("1) o que está acontecendo; 2) risco; 3) EPI faltando; 4) recomendação. ");
        prompt.append("\n\nDescrição: ").append(textPrompt == null ? "" : textPrompt);

        Prompt p = new Prompt(new UserMessage(prompt.toString()));
        return chatClient.prompt(p).call().content();
    }
}

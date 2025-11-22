package br.com.safework.controller;

import br.com.safework.service.AiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ai")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @GetMapping
    public String page(Model model) {
        // garante valores iniciais
        model.addAttribute("prompt", "");
        model.addAttribute("result", null);
        return "ai/page";
    }

    @PostMapping("/analyze")
    public String analyze(@RequestParam(required = false) String prompt,
                          Model model) {

        String result = aiService.analyze(prompt);
        model.addAttribute("result", result);
        model.addAttribute("prompt", prompt);

        return "ai/page";
    }
}

package br.com.safework.controller;

import br.com.safework.service.AiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/ai")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @GetMapping
    public String page() {
        return "ai/page";
    }

    @PostMapping("/analyze")
    public String analyze(@RequestParam(required = false) String prompt,
                          @RequestParam(required = false) MultipartFile image,
                          Model model) {

        String result = aiService.analyze(prompt, image);
        model.addAttribute("result", result);
        model.addAttribute("prompt", prompt);

        return "ai/page";
    }
}

package br.com.safework.controller;

import br.com.safework.dto.AnaliseEpiRequestDTO;
import br.com.safework.dto.AnaliseEpiResponseDTO;
import br.com.safework.service.AnaliseEpiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class AnaliseEpiController {

    private final AnaliseEpiService service;

    public AnaliseEpiController(AnaliseEpiService service) {
        this.service = service;
    }

    @PostMapping("/analise-epi")
    public AnaliseEpiResponseDTO analisar(@RequestBody AnaliseEpiRequestDTO request) {
        return service.analisarImagem(request);
    }

    @PostMapping("/analise-epi/upload")
    public ResponseEntity<AnaliseEpiResponseDTO> uploadImagem(
            @RequestParam("imagem") MultipartFile imagem) {

        AnaliseEpiResponseDTO resposta = service.analisarImagemLocal(imagem);
        return ResponseEntity.ok(resposta);
    }
}

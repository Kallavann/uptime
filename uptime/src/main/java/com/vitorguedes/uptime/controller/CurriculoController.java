package com.vitorguedes.uptime.controller;

import com.vitorguedes.uptime.service.CurriculoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/curriculo")
@CrossOrigin(origins = "*")
public class CurriculoController {

    private final CurriculoService curriculoService;

    public CurriculoController( CurriculoService curriculoService) {
        this.curriculoService = curriculoService;
    }

    @PostMapping
    public ResponseEntity<String> receberCurriculo(
            @RequestParam("arquivo") MultipartFile arquivo,
            @RequestParam("email") String email
    ) {

        if (arquivo.isEmpty()) {
            return ResponseEntity.badRequest().body("Nenhum arquivo foi enviado");
        }

        // valida tipo
        String tipo = arquivo.getContentType();
        if (tipo == null ||
                (!tipo.equals("application/pdf") &&
                        !tipo.equals("application/msword") &&
                        !tipo.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))) {

            return ResponseEntity.badRequest()
                    .body("Formato inválido. Envie PDF ou DOC/DOCX.");
        }

        // valida email
        if (email == null || !email.contains("@")) {
            return ResponseEntity.badRequest().body("Email inválido");
        }

        try {
            curriculoService.processar(email, arquivo);
            return ResponseEntity.ok("Currículo recebido com sucesso!");

        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body("Erro ao processar o currículo.");
        }
    }
}

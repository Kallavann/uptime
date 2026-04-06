package com.vitorguedes.uptime.controller;

import com.vitorguedes.uptime.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/curriculo")
@CrossOrigin(origins = "*")
public class CurriculoController {

    private final EmailService emailService;

    public CurriculoController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<String> receberCurriculo(
            @RequestParam("arquivo") MultipartFile arquivo,
            @RequestParam("email") String email
    ) {
        if (arquivo.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("Nenhum arquivo foi enviado");
        }

        try {
            emailService.enviarCurriculo(email, arquivo);
            return ResponseEntity.ok("Currículo recebido com sucesso!");

        } catch (MessagingException e) {
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
            return  ResponseEntity
                    .status(500)
                    .body("Erro ao enviar o e-mail. Tente novamente.");

        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            return ResponseEntity
                    .status(500)
                    .body("Erro interno. Tente novamente mais tarde.");
        }

    }
}

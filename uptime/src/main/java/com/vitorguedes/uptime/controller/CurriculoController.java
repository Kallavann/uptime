package com.vitorguedes.uptime.controller;

import com.vitorguedes.uptime.service.EmailService;
import com.vitorguedes.uptime.service.WhatsAppService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/curriculo")
@CrossOrigin(origins = "*")
public class CurriculoController {

    private final EmailService emailService;
    private final WhatsAppService whatsAppService;

    public CurriculoController(EmailService emailService, WhatsAppService whatsAppService) {
        this.emailService = emailService;
        this.whatsAppService = whatsAppService;
    }

    @PostMapping
    public ResponseEntity<String> receberCurriculo(
            @RequestParam("arquivo") MultipartFile arquivo,
            @RequestParam("email") String email
    ) throws Exception {

        emailService.enviarCurriculo(email, arquivo);

        String mensagem = String.format(
                "📄 *Novo Currículo - Uptime Consultoria*\n\n" +
                        "📧 Candidato: %s\n" +
                        "📎 Arquivo: %s\n\n" +
                        "✅ PDF enviado para o seu e-mail!",
                email, arquivo.getOriginalFilename()
        );
        whatsAppService.enviar(mensagem);

        return ResponseEntity.ok("Currículo recebido com sucesso!");
    }
}

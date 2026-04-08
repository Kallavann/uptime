package com.vitorguedes.uptime.service;

import com.vitorguedes.uptime.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CurriculoService {

    private final EmailService emailService;

    public CurriculoService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void processar(String email, MultipartFile arquivo) {
        try {
            emailService.enviarCurriculo(email, arquivo);
        } catch (RuntimeException e) {
            throw new RuntimeException("Falha ao processar currículo: " + e.getMessage(), e);
        }
    }
    }

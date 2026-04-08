package com.vitorguedes.uptime.service;

import com.vitorguedes.uptime.dto.ContatoDTO;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public class ContatoService {
    private final EmailService emailService;

    public ContatoService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void processarContato(ContatoDTO dto) throws MessagingException {
        // Aqui você pode adicionar regras futuramente
        emailService.enviarContato(dto);
    }
}


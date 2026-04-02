package com.vitorguedes.uptime.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailDestino;

    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void enviarCurriculo(String emailRemetente, MultipartFile arquivo)
        throws MessagingException{
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(emailDestino);
        helper.setSubject("📄 Novo Currículo Recebido - Uptime Consultoria");
        helper.setText("Novo currículo enviado para análise.\n\nE-mail do candidato: " + emailRemetente);
        helper.addAttachment(arquivo.getOriginalFilename(), arquivo);

        mailSender.send(message);
    }
}

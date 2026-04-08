package com.vitorguedes.uptime.service;

import com.vitorguedes.uptime.dto.ContatoDTO;
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

    public void enviarContato(ContatoDTO dto) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(emailDestino);
            helper.setTo(emailDestino);
            helper.setSubject("🔔 Novo Contato - Uptime Consultoria");

            helper.setText("""
                <h2>📩 Novo Contato</h2>
                <p><b>Nome:</b> %s</p>
                <p><b>Email:</b> %s</p>
                <p><b>Telefone:</b> %s</p>
                <hr>
                <p><b>Mensagem:</b></p>
                <p>%s</p>
            """.formatted(dto.nome(), dto.email(), dto.telefone(), dto.mensagem()), true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar e-mail de contato", e);
        }
    }

    public void enviarCurriculo(String emailRemetente, MultipartFile arquivo) {
        try {
            if (arquivo.isEmpty()) {
                throw new IllegalArgumentException("Arquivo vazio");
            }

            String nomeArquivo = arquivo.getOriginalFilename() != null
                    ? arquivo.getOriginalFilename()
                    : "curriculo.pdf";

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(emailDestino);
            helper.setTo(emailDestino);
            helper.setSubject("📄 Novo Currículo Recebido");

            helper.setText("E-mail do candidato: " + emailRemetente);
            helper.addAttachment(nomeArquivo, arquivo);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar currículo", e);
        }
    }
}
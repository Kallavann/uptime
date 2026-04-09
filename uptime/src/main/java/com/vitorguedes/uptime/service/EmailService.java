package com.vitorguedes.uptime.service;

import com.vitorguedes.uptime.dto.ContatoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@Service
public class EmailService {

    @Value("${RESEND_API_KEY}")
    private String apiKey;

    private final String RESEND_URL = "https://api.resend.com/emails";

    public void enviarContato(ContatoDTO dto) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            String body = """
            {
              "from": "Uptime <onboarding@resend.dev>",
              "to": ["consultoriauptimee@gmail.com"],
              "subject": "Novo Contato - Uptime",
              "html": "<h2>Novo Contato</h2><p><b>Nome:</b> %s</p><p><b>Email:</b> %s</p><p><b>Telefone:</b> %s</p><p><b>Mensagem:</b> %s</p>"
            }
            """.formatted(dto.nome(), dto.email(), dto.telefone(), dto.mensagem());

            HttpEntity<String> request = new HttpEntity<>(body, headers);
            restTemplate.postForEntity(RESEND_URL, request, String.class);

        } catch (Exception e) {
            System.out.println("Erro ao enviar contato:");
            e.printStackTrace();
            throw new RuntimeException("Erro ao enviar contato", e);
        }
    }

    public void enviarCurriculo(String emailRemetente, MultipartFile arquivo) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            String arquivoBase64 = Base64.getEncoder().encodeToString(arquivo.getBytes());
            String nomeArquivo = arquivo.getOriginalFilename() != null
                    ? arquivo.getOriginalFilename()
                    : "curriculo.pdf";

            String body = """
            {
              "from": "Uptime <onboarding@resend.dev>",
              "to": ["consultoriauptimee@gmail.com"],
              "subject": "Novo Currículo Recebido",
              "html": "<p><b>Email do candidato:</b> %s</p>",
              "attachments": [
                {
                  "filename": "%s",
                  "content": "%s"
                }
              ]
            }
            """.formatted(emailRemetente, nomeArquivo, arquivoBase64);

            HttpEntity<String> request = new HttpEntity<>(body, headers);
            restTemplate.postForEntity(RESEND_URL, request, String.class);

        } catch (Exception e) {
            System.out.println("Erro ao enviar currículo:");
            e.printStackTrace();
            throw new RuntimeException("Erro ao enviar currículo", e);
        }
    }
}
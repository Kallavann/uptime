package com.vitorguedes.uptime.controller;


import com.vitorguedes.uptime.dto.ContatoDTO;
import com.vitorguedes.uptime.service.WhatsAppService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contato")
@CrossOrigin(origins = "*")
public class ContatoController {

    private final WhatsAppService whatsAppService;

    public ContatoController(WhatsAppService whatsAppService){
        this.whatsAppService = whatsAppService;
    }
        @PostMapping
        public ResponseEntity<String> receberContato(@RequestBody ContatoDTO dto) {
        String mensagem = String.format(
                "🔔 *Novo contato - Uptime Consultoria*\n\n" +
                        "👤 Nome: %s\n" +
                        "📧 E-mail: %s\n" +
                        "📱 Telefone: %s\n" +
                        "💬 Mensagem: %s",
                dto.nome(), dto.email(), dto.telefone(), dto.mensagem()
        );
        whatsAppService.enviar(mensagem);
        return ResponseEntity.ok("Contato recebido com sucesso!");

        }
    }

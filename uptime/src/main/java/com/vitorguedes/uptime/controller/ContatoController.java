package com.vitorguedes.uptime.controller;


import com.vitorguedes.uptime.dto.ContatoDTO;
import com.vitorguedes.uptime.service.ContatoService;
import com.vitorguedes.uptime.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contato")
@CrossOrigin(origins = "https://uptime-mentoria.vercel.app")
public class ContatoController {

    private final ContatoService contatoService;

    public ContatoController(ContatoService contatoService) {
        this.contatoService = contatoService;
    }

    @PostMapping
    public ResponseEntity<String> receberContato(@RequestBody ContatoDTO dto) {
        try {
            contatoService.processarContato(dto);
            return ResponseEntity.ok("Mensagem recebida com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(500)
                    .body("Erro ao enviar mensagem");

        }
    }
}
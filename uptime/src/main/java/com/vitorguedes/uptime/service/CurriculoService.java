package com.vitorguedes.uptime.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class CurriculoService {

    private final CurriculoService curriculoService;

    public void processar(String email, MultipartFile arquivo) {
        curriculoService.curriculoService.processar(email, arquivo);
    }
}

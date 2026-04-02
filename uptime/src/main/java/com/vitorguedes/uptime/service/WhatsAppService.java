package com.vitorguedes.uptime.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.aut-token}")
    private String authToken;

    @Value("${twilio.whatsapp-from}")
    private String from;

    @Value("${twilio.whatsapp-to}")
    private String to;

    public void enviar(String mensagem) {
        Twilio.init(accountSid, authToken);
        Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(from),
                mensagem
        ).create();
    }
}

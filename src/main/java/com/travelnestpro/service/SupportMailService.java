package com.travelnestpro.service;

import com.travelnestpro.dto.ContactEmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SupportMailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SupportMailService.class);

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String fromAddress;

    @Value("${app.mail.support-to}")
    private String supportTo;

    public SupportMailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendContactEmail(ContactEmailRequest request) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromAddress);
            message.setTo(supportTo);
            message.setReplyTo(request.getEmail());
            message.setSubject("[TravelNestPro] " + request.getSubject());
            message.setText("""
                    Name: %s
                    Email: %s

                    %s
                    """.formatted(request.getName(), request.getEmail(), request.getMessage()));
            mailSender.send(message);
        } catch (Exception ex) {
            LOGGER.warn("Mail delivery failed, logging support request instead: {}", ex.getMessage());
            LOGGER.info("Support request from {} <{}>: {}", request.getName(), request.getEmail(), request.getMessage());
        }
    }
}

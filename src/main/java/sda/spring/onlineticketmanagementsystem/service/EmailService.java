package sda.spring.onlineticketmanagementsystem.service;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import sda.spring.onlineticketmanagementsystem.dto.common.EmailMessageDto;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void sendEmail(EmailMessageDto emailMessage){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailMessage.getTo());
        message.setFrom(emailMessage.getFrom());
        message.setSubject(emailMessage.getSubject());
        message.setText(emailMessage.getBody());

        mailSender.send(message);
    }
}

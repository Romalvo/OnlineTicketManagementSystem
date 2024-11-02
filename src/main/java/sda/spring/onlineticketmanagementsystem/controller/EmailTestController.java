package sda.spring.onlineticketmanagementsystem.controller;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sda.spring.onlineticketmanagementsystem.dto.common.EmailMessageDto;
import sda.spring.onlineticketmanagementsystem.service.EmailService;

@RestController
@RequestMapping("/api/v1/email")
public class EmailTestController {

    private final EmailService emailService;

    public EmailTestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    // @RequestBody -> APIs (json)
    // @ModelAttribute -> html forms (form-data)
    public void sendTestEmail(@ModelAttribute EmailMessageDto emailMessageDto) {
        emailService.sendEmail(emailMessageDto);
    }
}

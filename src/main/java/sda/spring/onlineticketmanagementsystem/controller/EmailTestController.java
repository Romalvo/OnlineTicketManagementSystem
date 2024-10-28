package sda.spring.onlineticketmanagementsystem.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
public class EmailTestController {

    @PostMapping
    public void sendTestEmail(@RequestParam String to, String subject, String body) {}
}

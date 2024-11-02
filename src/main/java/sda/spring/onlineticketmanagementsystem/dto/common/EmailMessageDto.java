package sda.spring.onlineticketmanagementsystem.dto.common;


import lombok.Data;

@Data
public class EmailMessageDto {

    private String to;
    private String from;
    private String subject;
    private String body;
}

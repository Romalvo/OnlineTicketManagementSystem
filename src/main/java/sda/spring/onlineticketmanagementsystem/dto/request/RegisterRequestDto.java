package sda.spring.onlineticketmanagementsystem.dto.request;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
public class RegisterRequestDto {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
}

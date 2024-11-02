package sda.spring.onlineticketmanagementsystem.dto.request;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginRequestDto {

    private String email;
    private String password;
}

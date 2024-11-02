package sda.spring.onlineticketmanagementsystem.dto.common;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserProfileDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
}

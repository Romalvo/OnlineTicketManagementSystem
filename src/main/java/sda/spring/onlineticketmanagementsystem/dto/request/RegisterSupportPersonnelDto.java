package sda.spring.onlineticketmanagementsystem.dto.request;


import lombok.Data;

import java.util.Set;

@Data
public class RegisterSupportPersonnelDto {

    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private Set<String> departments;
}

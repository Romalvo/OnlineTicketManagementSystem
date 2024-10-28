package sda.spring.onlineticketmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String type;
    private LocalDateTime createdAt;
}

package sda.spring.onlineticketmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Ticket ticket;

    @ManyToOne
    private User sender; // It can be either Customer or support person

    private String text;

    private LocalDateTime createdAt;

}

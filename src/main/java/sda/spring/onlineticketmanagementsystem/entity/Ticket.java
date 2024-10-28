package sda.spring.onlineticketmanagementsystem.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String subject;
    private Long departmentId;
    private String status;
    private String priority;
    private Long customerUserId;
    private Long supportUserId;
    private LocalDateTime createdAt;
}

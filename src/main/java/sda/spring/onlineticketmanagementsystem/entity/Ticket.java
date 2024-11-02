package sda.spring.onlineticketmanagementsystem.entity;



import jakarta.annotation.Priority;
import jakarta.persistence.*;
import jakarta.transaction.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Department department;

    @ManyToOne
    private SupportPersonnel supportPersonnel;

    private String subject;
    private String description;
    private Priority priority;
    private Status status;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();
    private LocalDateTime createdAt;

    enum Status {
        OPEN,
        CLOSED,
    }
    enum Priority {
        HIGH,
        MEDIUM,
        LOW,
    }
}



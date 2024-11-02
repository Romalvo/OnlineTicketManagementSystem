package sda.spring.onlineticketmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "departments")
    private Set<SupportPersonnel> supportPersonnels;
}

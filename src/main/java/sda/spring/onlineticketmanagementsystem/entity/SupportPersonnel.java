package sda.spring.onlineticketmanagementsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
public class SupportPersonnel extends User {

    @ManyToMany
    @JoinTable(
            name="support_personnel_department",
            joinColumns = @JoinColumn(name = "support_personnel_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )

    private Set<SupportPersonnel> departments;
}

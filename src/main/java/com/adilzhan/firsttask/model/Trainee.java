package com.adilzhan.firsttask.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trainees")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Trainee extends User {

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String address;

    @ManyToMany(mappedBy = "trainees")
    @ToString.Exclude
    private Set<Trainer> trainers = new HashSet<>();

    public Trainee(String id, String firstName, String lastName, String username, String password, boolean isActive, LocalDate dateOfBirth, String address) {
        super(id, firstName, lastName, username, password, isActive);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }
}

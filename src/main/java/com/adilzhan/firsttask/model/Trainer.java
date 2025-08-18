package com.adilzhan.firsttask.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import lombok.*;

@Entity
@Table(name = "trainers")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Trainer extends User {

    @Column(nullable = false)
    private String specialization;

    @ManyToMany
    @JoinTable(
            name = "trainer_trainee",
            joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name = "trainee_id")
    )
    @ToString.Exclude
    private Set<Trainee> trainees = new HashSet<>();

    public Trainer(String id,
                   String firstName,
                   String lastName,
                   String username,
                   String password,
                   boolean isActive,
                   String specialization) {
        super(id, firstName, lastName, username, password, isActive);
        this.specialization = specialization;
    }

}

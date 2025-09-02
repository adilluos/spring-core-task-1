package com.adilzhan.firsttask.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import lombok.*;

@Entity
@Table(name = "trainers")
@PrimaryKeyJoinColumn(name = "id") //PK of this is also FK for 'users'. Also we customized name
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Trainer extends User {

    @Column(nullable = false)
    private String specialization;

    @ManyToMany(mappedBy = "trainers") //The parameter has the string of the field in the "owning" class. in this case, it is Set of trainers
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

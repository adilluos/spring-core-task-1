package com.adilzhan.firsttask.model;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;
import lombok.*;

@Entity
@Table(name = "trainings")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Training {

    @Id
    @Column(length = 36)
    @EqualsAndHashCode.Include
    private String id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "trainer_id")
    @ToString.Exclude
    private Trainer trainer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "trainee_id")
    @ToString.Exclude
    private Trainee trainee;

    @ManyToOne(optional = false)
    @JoinColumn(name = "training_type_id")
    private TrainingType trainingType;

    @Column(nullable = false)
    private LocalDate trainingDate;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private String description;
}

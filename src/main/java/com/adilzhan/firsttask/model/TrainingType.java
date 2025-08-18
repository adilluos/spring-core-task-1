package com.adilzhan.firsttask.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "training_types")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TrainingType {

    @Id
    @Column(length = 36)
    private String id;

    @EqualsAndHashCode.Include
    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;
}

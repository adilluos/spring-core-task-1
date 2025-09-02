package com.adilzhan.firsttask.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = false) //No parent fields in toString
@EqualsAndHashCode(onlyExplicitlyIncluded = true) //In this class, equals and hashcode will be built only by marked fields (below)
public abstract class User {
    @Id
    @Column
    @EqualsAndHashCode.Include //This marked field. By the way, it is a good practice for entities to have eq and hc only by id
    private String id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isActive;

}

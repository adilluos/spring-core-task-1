package com.adilzhan.firsttask.model;

import java.util.Objects;

public class Trainer extends User {
    private String specialization;
    private String userId;

    public Trainer() {}

    public Trainer(String firstName, String lastName, String username, String password, boolean isActive, String specialization, String userId) {
        super(firstName, lastName, username, password, isActive);
        this.specialization = specialization;
        this.userId = userId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Trainer trainer = (Trainer) o;
        return Objects.equals(specialization, trainer.specialization) && Objects.equals(userId, trainer.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), specialization, userId);
    }

    @Override
    public String toString() {
        return super.toString().replace("}", "") +
                ", specialization='" + specialization + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}

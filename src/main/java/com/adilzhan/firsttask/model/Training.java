package com.adilzhan.firsttask.model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

public class Training {
    private String id;
    private String trainerUserId;
    private String traineeUserId;
    private String trainingName;
    private TrainingType trainingType;
    private LocalDate trainingDate;
    private Duration trainingDuration;

    public Training() {}

    public Training(String id, String trainerUserId, String traineeUserId, String trainingName, TrainingType trainingType, LocalDate trainingDate, Duration trainingDuration) {
        this.id = id;
        this.trainerUserId = trainerUserId;
        this.traineeUserId = traineeUserId;
        this.trainingName = trainingName;
        this.trainingType = trainingType;
        this.trainingDate = trainingDate;
        this.trainingDuration = trainingDuration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainerUserId() {
        return trainerUserId;
    }

    public void setTrainerUserId(String trainerUserId) {
        this.trainerUserId = trainerUserId;
    }

    public String getTraineeUserId() {
        return traineeUserId;
    }

    public void setTraineeUserId(String traineeUserId) {
        this.traineeUserId = traineeUserId;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public LocalDate getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(LocalDate trainingDate) {
        this.trainingDate = trainingDate;
    }

    public Duration getTrainingDuration() {
        return trainingDuration;
    }

    public void setTrainingDuration(Duration trainingDuration) {
        this.trainingDuration = trainingDuration;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Training training = (Training) o;
        return Objects.equals(id, training.id) && Objects.equals(trainerUserId, training.trainerUserId) && Objects.equals(traineeUserId, training.traineeUserId) && Objects.equals(trainingName, training.trainingName) && trainingType == training.trainingType && Objects.equals(trainingDate, training.trainingDate) && Objects.equals(trainingDuration, training.trainingDuration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trainerUserId, traineeUserId, trainingName, trainingType, trainingDate, trainingDuration);
    }

    @Override
    public String toString() {
        return "Training{" +
                "id='" + id + '\'' +
                ", trainerUserId='" + trainerUserId + '\'' +
                ", traineeUserId='" + traineeUserId + '\'' +
                ", trainingName='" + trainingName + '\'' +
                ", trainingType=" + trainingType +
                ", trainingDate=" + trainingDate +
                ", trainingDuration=" + trainingDuration +
                '}';
    }
}

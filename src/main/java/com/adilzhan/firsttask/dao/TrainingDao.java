package com.adilzhan.firsttask.dao;

import com.adilzhan.firsttask.model.Training;

import java.util.List;
import java.util.Optional;

public interface TrainingDao {
    void save(Training training);
    Optional<Training> findById(String trainingId);
    List<Training> findAll();
}

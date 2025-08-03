package com.adilzhan.firsttask.dao;

import com.adilzhan.firsttask.model.Trainee;

import java.util.List;
import java.util.Optional;

public interface TraineeDao {
    void save(Trainee trainee);
    void update(Trainee trainee);
    void delete(String userId);
    Optional<Trainee> findById(String userId);
    List<Trainee> findAll();
}

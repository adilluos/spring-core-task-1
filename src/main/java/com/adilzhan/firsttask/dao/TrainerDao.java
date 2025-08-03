package com.adilzhan.firsttask.dao;

import com.adilzhan.firsttask.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerDao {
    void save(Trainer trainer);
    void update(Trainer trainer);
    Optional<Trainer> findById(String userId);
    List<Trainer> findAll();
}

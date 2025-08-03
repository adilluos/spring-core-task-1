package com.adilzhan.firsttask.dao.impl;

import com.adilzhan.firsttask.dao.TrainingDao;
import com.adilzhan.firsttask.model.Training;
import com.adilzhan.firsttask.storage.InMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TrainingDaoImpl implements TrainingDao {

    private InMemoryStorage storage;

    @Autowired
    public void setStorage(InMemoryStorage storage) {
        this.storage = storage;
    }

    @Override
    public void save(Training training) {
        storage.getTrainingMap().put(training.getId(), training);
    }

    @Override
    public Optional<Training> findById(String trainingId) {
        return Optional.ofNullable(storage.getTrainingMap().get(trainingId));
    }

    @Override
    public List<Training> findAll() {
        return new ArrayList<>(storage.getTrainingMap().values());
    }
}

package com.adilzhan.firsttask.dao.impl;

import com.adilzhan.firsttask.dao.TraineeDao;
import com.adilzhan.firsttask.model.Trainee;
import com.adilzhan.firsttask.storage.InMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TraineeDaoImpl implements TraineeDao {

    private InMemoryStorage storage;

    @Autowired
    public void setStorage(InMemoryStorage storage) {
        this.storage = storage;
    }
    @Override
    public void save(Trainee trainee) {
        storage.getTraineeMap().put(trainee.getUserId(), trainee);
    }

    @Override
    public void update(Trainee trainee) {
        storage.getTraineeMap().put(trainee.getUserId(), trainee);
    }

    @Override
    public void delete(String userId) {
        storage.getTraineeMap().remove(userId);
    }

    @Override
    public Optional<Trainee> findById(String userId) {
        return Optional.ofNullable(storage.getTraineeMap().get(userId));
    }

    @Override
    public List<Trainee> findAll() {
        return new ArrayList<>(storage.getTraineeMap().values());
    }
}

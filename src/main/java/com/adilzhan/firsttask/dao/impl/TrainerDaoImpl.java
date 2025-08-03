package com.adilzhan.firsttask.dao.impl;

import com.adilzhan.firsttask.dao.TrainerDao;
import com.adilzhan.firsttask.model.Trainer;
import com.adilzhan.firsttask.storage.InMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TrainerDaoImpl implements TrainerDao {

    private InMemoryStorage storage;

    @Autowired
    public void setStorage(InMemoryStorage storage) {
        this.storage = storage;
    }

    @Override
    public void save(Trainer trainer) {
        storage.getTrainerMap().put(trainer.getUserId(), trainer);
    }

    @Override
    public void update(Trainer trainer) {
        storage.getTrainerMap().put(trainer.getUserId(), trainer);
    }

    @Override
    public Optional<Trainer> findById(String userId) {
        return Optional.ofNullable(storage.getTrainerMap().get(userId));
    }

    @Override
    public List<Trainer> findAll() {
        return new ArrayList<>(storage.getTrainerMap().values());
    }
}

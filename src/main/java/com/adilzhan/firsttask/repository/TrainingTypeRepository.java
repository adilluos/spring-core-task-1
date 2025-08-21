package com.adilzhan.firsttask.repository;

import com.adilzhan.firsttask.model.Trainer;
import com.adilzhan.firsttask.model.TrainingType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainingTypeRepository extends JpaRepository<TrainingType, String> {
    Optional<TrainingType> findByCode(String code);

}

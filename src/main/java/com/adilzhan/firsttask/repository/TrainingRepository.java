package com.adilzhan.firsttask.repository;

import com.adilzhan.firsttask.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training, String> {
}

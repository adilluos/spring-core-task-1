package com.adilzhan.firsttask.repository;

import com.adilzhan.firsttask.model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, String> {
    Optional<Trainee> findByUsername(String username);
}

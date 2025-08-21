package com.adilzhan.firsttask.repository;

import com.adilzhan.firsttask.dto.TrainerOption;
import com.adilzhan.firsttask.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, String> {
    Optional<Trainer> findByUsername(String username);

    @Query("""
        select new com.adilzhan.firsttask.dto.TrainerOption(
            tr.username, tr.firstName, tr.lastName, tr.specialization
        )
        from Trainer tr
        left join tr.trainees t
            with t.username = :traineeUsername
        where tr.isActive = true
          and t is null
        order by tr.lastName, tr.firstName
        """)
    List<TrainerOption> findActiveNotAssignedTo(@Param("traineeUsername") String traineeUsername);
}

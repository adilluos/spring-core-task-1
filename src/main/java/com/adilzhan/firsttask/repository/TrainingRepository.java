package com.adilzhan.firsttask.repository;

import com.adilzhan.firsttask.dto.TrainingRow;
import com.adilzhan.firsttask.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, String> {
    @Query("""
        select new com.adilzhan.firsttask.dto.TrainingRow(
            t.description,
            t.trainingDate,
            tt.name,
            t.duration,
            concat(tr.firstName, ' ', tr.lastName)
        )
        from Training t
          join t.trainingType tt
          join t.trainer tr
          join t.trainee tn
        where tn.username = :username
          and t.trainingDate >= coalesce(:fromDate, t.trainingDate)
          and t.trainingDate <= coalesce(:toDate,   t.trainingDate)
          and (:trainerNameLike is null
               or lower(tr.firstName) like :trainerNameLike
               or lower(tr.lastName)  like :trainerNameLike)
          and (:trainingTypeLower is null or lower(tt.name) = :trainingTypeLower)
        order by t.trainingDate desc
        """)
    List<TrainingRow> findTraineeTrainings(
            @Param("username") String username,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            @Param("trainerNameLike") String trainerNameLike,
            @Param("trainingTypeLower") String trainingTypeLower
    );

    @Query("""
        select new com.adilzhan.firsttask.dto.TrainingRow(
            t.description,
            t.trainingDate,
            tt.name,
            t.duration,
            concat(tn.firstName, ' ', tn.lastName)
        )
        from Training t
          join t.trainingType tt
          join t.trainer tr
          join t.trainee tn
        where tr.username = :username
          and t.trainingDate >= coalesce(:fromDate, t.trainingDate)
          and t.trainingDate <= coalesce(:toDate,   t.trainingDate)
          and (:traineeNameLike is null
               or lower(tn.firstName) like :traineeNameLike
               or lower(tn.lastName)  like :traineeNameLike)
        order by t.trainingDate desc
        """)
    List<TrainingRow> findTrainerTrainings(
            @Param("username") String username,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            @Param("traineeNameLike") String traineeNameLike
    );
}

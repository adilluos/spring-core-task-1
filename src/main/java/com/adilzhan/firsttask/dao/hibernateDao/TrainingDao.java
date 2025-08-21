//package com.adilzhan.firsttask.dao.hibernateDao;
//
//import com.adilzhan.firsttask.model.Trainee;
//import com.adilzhan.firsttask.model.Trainer;
//import com.adilzhan.firsttask.model.Training;
//import com.adilzhan.firsttask.model.TrainingType;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.persistence.criteria.*;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public class TrainingDao {
//
//    @PersistenceContext
//    private EntityManager em;
//
//    public void save(Training training) {
//        em.persist(training);
//    }
//
//    public Training update(Training training) {
//        return em.merge(training);
//    }
//
//    public Optional<Training> findById(String id) {
//        return Optional.ofNullable(em.find(Training.class, id));
//    }
//
//    public List<Training> findAll() {
//        return em.createQuery("select t from trainings t order by t.trainingDate asc", Training.class)
//                .getResultList();
//    }
//
//    public void delete(Training training) {
//        em.remove(em.contains(training) ? training : em.merge(training));
//    }
//
//    public void deleteById(String id) {
//        findById(id).ifPresent(this::delete);
//    }
//
//    public List<Training> findForTrainee(
//            String traineeUsername,
//            LocalDate fromDate,
//            LocalDate toDate,
//            String trainerNameLike,
//            String typeCode
//    ) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Training> cq = cb.createQuery(Training.class);
//        Root<Training> root = cq.from(Training.class);
//
//        List<Predicate> ps = new ArrayList<>();
//        Path<Trainee> trainee = root.get("trainee");
//        Path<Trainer> trainer = root.get("trainer");
//        Path<TrainingType> type = root.get("trainingType");
//
//        ps.add(cb.equal(trainee.get("username"), traineeUsername));
//
//        if (fromDate != null) ps.add(cb.greaterThanOrEqualTo(root.get("trainingDate"), fromDate));
//        if (toDate != null) ps.add(cb.lessThanOrEqualTo(root.get("trainingDate"), toDate));
//
//        if (trainerNameLike != null && !trainerNameLike.isBlank()) {
//            Expression<String> fullName = cb.concat(cb.concat(trainer.get("firstName"), " "), trainer.get("lastName"));
//            ps.add(cb.like(cb.lower(fullName), "%" + trainerNameLike.toLowerCase() + "%"));
//        }
//
//        if (typeCode != null && !typeCode.isBlank()) {
//            ps.add(cb.equal(type.get("code"), typeCode));
//        }
//
//        cq.select(root).where(ps.toArray(Predicate[]::new)).orderBy(cb.asc(root.get("trainingDate")));
//        return em.createQuery(cq).getResultList();
//    }
//
//    public List<Training> findForTrainer(
//            String trainerUsername,
//            LocalDate fromDate,
//            LocalDate toDate,
//            String traineeNameLike,
//            String typeCode
//    ) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Training> cq = cb.createQuery(Training.class);
//        Root<Training> root = cq.from(Training.class);
//
//        List<Predicate> ps = new ArrayList<>();
//        Path<Trainer> trainer = root.get("trainer");
//        Path<Trainee> trainee = root.get("trainee");
//        Path<TrainingType> type = root.get("trainingType");
//
//        ps.add(cb.equal(trainer.get("username"), trainerUsername));
//
//        if (fromDate != null) ps.add(cb.greaterThanOrEqualTo(root.get("trainingDate"), fromDate));
//        if (toDate != null)   ps.add(cb.lessThanOrEqualTo(root.get("trainingDate"), toDate));
//
//        if (traineeNameLike != null && !traineeNameLike.isBlank()) {
//            Expression<String> fullName = cb.concat(cb.concat(trainee.get("firstName"), " "), trainee.get("lastName"));
//            ps.add(cb.like(cb.lower(fullName), "%" + traineeNameLike.toLowerCase() + "%"));
//        }
//
//        if (typeCode != null && !typeCode.isBlank()) {
//            ps.add(cb.equal(type.get("code"), typeCode));
//        }
//
//        cq.select(root).where(ps.toArray(Predicate[]::new)).orderBy(cb.asc(root.get("trainingDate")));
//        return em.createQuery(cq).getResultList();
//    }
//}

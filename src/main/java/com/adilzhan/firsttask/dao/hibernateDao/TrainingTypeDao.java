//package com.adilzhan.firsttask.dao.hibernateDao;
//
//import com.adilzhan.firsttask.model.TrainingType;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public class TrainingTypeDao {
//
//    @PersistenceContext
//    private EntityManager em;
//
//    public void save(TrainingType trainingType) {
//        em.persist(trainingType);
//    }
//
//    public void update(TrainingType trainingType) {
//        em.merge(trainingType);
//    }
//
//    public Optional<TrainingType> findById(String id) {
//        return Optional.ofNullable(em.find(TrainingType.class, id));
//    }
//
//    public Optional<TrainingType> findByCode(String code) {
//        return em.createQuery("select t from TrainingType t where t.code = :c", TrainingType.class)
//                .setParameter("c", code)
//                .getResultStream()
//                .findFirst();
//    }
//
//    public List<TrainingType> findAll() {
//        return em.createQuery("select t from TrainingType t order by t.code", TrainingType.class)
//                .getResultList();
//    }
//
//    public void delete(TrainingType trainingType) {
//        em.remove(em.contains(trainingType) ? trainingType : em.merge(trainingType));
//    }
//
//    public void deleteById(String id) {
//        findById(id).ifPresent(this::delete);
//    }
//
//    public void deleteByCode(String code) {
//        findByCode(code).ifPresent(this::delete);
//    }
//}

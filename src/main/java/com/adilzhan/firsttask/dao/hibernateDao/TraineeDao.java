//package com.adilzhan.firsttask.dao.hibernateDao;
//
//import com.adilzhan.firsttask.model.Trainee;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public class TraineeDao {
//    @PersistenceContext
//    private EntityManager em;
//
//    public void save(Trainee trainee) {
//        em.persist(trainee);
//    }
//
//    public void update(Trainee trainee) {
//        em.merge(trainee);
//    }
//
//    public Optional<Trainee> findById(String id) {
//        return Optional.ofNullable(em.find(Trainee.class, id));
//    }
//
//    public Optional<Trainee> findByUsername(String username) {
//        return em.createQuery("select t from trainees t where t.username = :u", Trainee.class)
//                .setParameter("u", username)
//                .getResultStream()
//                .findFirst();
//    }
//
//    public List<Trainee> findAll() {
//        return em.createQuery("select t from trainees t order by t.lastName, t.firstName", Trainee.class)
//                .getResultList();
//    }
//
//    public void delete(Trainee trainee) {
//        em.remove(em.contains(trainee) ? trainee : em.merge(trainee));
//    }
//
//    public void deleteById(String id) {
//        findById(id).ifPresent(this::delete);
//    }
//
//    public void deleteByUsername(String username) {
//        findByUsername(username).ifPresent(this::delete);
//    }
//}

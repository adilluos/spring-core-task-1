package com.adilzhan.firsttask.dao.hibernateDao;

import com.adilzhan.firsttask.model.Trainer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainerDao {
    @PersistenceContext
    private EntityManager em;

    public void save(Trainer trainer) {
        em.persist(trainer);
    }

    public void update(Trainer trainer) {
        em.merge(trainer);
    }

    public Optional<Trainer> findById(String id) {
        return Optional.ofNullable(em.find(Trainer.class, id));
    }

    public Optional<Trainer> findByUsername(String username) {
        return em.createQuery("select t from trainers t where t.username = :u", Trainer.class)
                .setParameter("u", username)
                .getResultStream()
                .findFirst();
    }

    public List<Trainer> findAll() {
        return em.createQuery("select t from trainers t order by t.lastName, t.firstName", Trainer.class)
                .getResultList();
    }

    public void delete(Trainer trainer) {
        em.remove(em.contains(trainer) ? trainer : em.merge(trainer));
    }

    public void deleteById(String id) {
        findById(id).ifPresent(this::delete);
    }

    public void deleteByUsername(String username) {
        findByUsername(username).ifPresent(this::delete);
    }

    public List<Trainer> findUnassignedForTrainee(String traineeUsername) {
        return em.createQuery("""
            select tr from trainers tr
            where tr not in (
                select t2 from Trainee ta join ta.trainers t2
                where ta.username = :u
            )
        """, Trainer.class).setParameter("u", traineeUsername).getResultList();
    }
}

package com.adilzhan.firsttask.dao.hibernateDao;

import com.adilzhan.firsttask.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public void update(User user) {
        em.merge(user);
    }

    public Optional<User> findById(String id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    public Optional<User> findByUsername(String username) {
        return em.createQuery("select u from users u where u.username = :u", User.class)
                .setParameter("u", username)
                .getResultStream()
                .findFirst();
    }

    public boolean existsByUsername(String username) {
        Long count = em.createQuery("select count(u) from users u where u.username = :u", Long.class)
                .setParameter("u", username)
                .getSingleResult();
        return count > 0;
    }

    public List<User> findAll() {
        return em.createQuery("select u from users u order by u.lastName, u.firstName", User.class)
                .getResultList();
    }

    public void delete(User user) {
        em.remove(em.contains(user) ? user : em.merge(user));
    }

    public void deleteById(String id) {
        findById(id).ifPresent(this::delete);
    }

    public void deleteByUsername(String username) {
        findByUsername(username).ifPresent(this::delete);
    }
}

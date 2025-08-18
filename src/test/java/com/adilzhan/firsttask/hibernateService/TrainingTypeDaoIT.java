package com.adilzhan.firsttask.hibernateService;

import com.adilzhan.firsttask.FirsttaskApplication;
import com.adilzhan.firsttask.dao.hibernateDao.TrainingTypeDao;
import com.adilzhan.firsttask.model.TrainingType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrainingTypeDaoIT {

    private static void setPrivate(Object target, String fieldName, Object value) {
        try {
            Field f = target.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void findByCode_returnsEntityWhenPresent() {
        EntityManager em = mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<TrainingType> q = mock(TypedQuery.class);

        TrainingTypeDao dao = new TrainingTypeDao();
        setPrivate(dao, "em", em);

        String jpql = "select t from TrainingType t where t.code = :c";
        when(em.createQuery(jpql, TrainingType.class)).thenReturn(q);
        when(q.setParameter("c", "YOGA")).thenReturn(q);

        TrainingType yoga = new TrainingType(UUID.randomUUID().toString(), "YOGA", "Yoga");
        when(q.getResultStream()).thenReturn(Stream.of(yoga));

        Optional<TrainingType> found = dao.findByCode("YOGA");

        assertTrue(found.isPresent());
        assertEquals("YOGA", found.get().getCode());
        verify(em).createQuery(jpql, TrainingType.class);
        verify(q).setParameter("c", "YOGA");
        verify(q).getResultStream();
    }

    @Test
    public void findByCode_returnsEmptyWhenNotFound() {
        EntityManager em = mock(EntityManager.class);
        @SuppressWarnings("unchecked")
        TypedQuery<TrainingType> q = mock(TypedQuery.class);
        TrainingTypeDao dao = new TrainingTypeDao();
        setPrivate(dao, "em", em);
        String jpql = "select t from TrainingType t where t.code = :c";
        when(em.createQuery(jpql, TrainingType.class)).thenReturn(q);
        when(q.setParameter("c", "NOPE")).thenReturn(q);
        when(q.getResultStream()).thenReturn(Stream.empty());

        Optional<TrainingType> found = dao.findByCode("NOPE");

        assertTrue(found.isEmpty());
        verify(em).createQuery(jpql, TrainingType.class);
        verify(q).setParameter("c", "NOPE");
        verify(q).getResultStream();
    }

    @Test
    public void save_callsPersist() {
        EntityManager em = mock(EntityManager.class);
        TrainingTypeDao dao = new TrainingTypeDao();
        setPrivate(dao, "em", em);

        TrainingType t = new TrainingType(UUID.randomUUID().toString(), "CARDIO", "Cardio");

        dao.save(t);

        verify(em).persist(t);
    }
}
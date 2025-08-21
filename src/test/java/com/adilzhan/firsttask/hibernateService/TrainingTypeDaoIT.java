package com.adilzhan.firsttask.hibernateService;

import com.adilzhan.firsttask.FirsttaskApplication;
import com.adilzhan.firsttask.dao.hibernateDao.TrainingTypeDao;
import com.adilzhan.firsttask.model.TrainingType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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

@SpringBootTest(classes = com.adilzhan.firsttask.FirsttaskApplication.class)
@Testcontainers
public class TrainingTypeDaoIT {

    @Container
    static PostgreSQLContainer<?> pg = new PostgreSQLContainer<>("postgres:16");

//    @Autowired
//    TrainingTypeDao typeDao;

    @DynamicPropertySource
    static void dbProps(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url", pg::getJdbcUrl);
        r.add("spring.datasource.username", pg::getUsername);
        r.add("spring.datasource.password", pg::getPassword);
        r.add("spring.jpa.hibernate.ddl-auto", () -> "update");
        r.add("spring.test.database.replace", () -> "NONE");
    }

    @Autowired
    TrainingTypeDao typeDao;

    @Test
    public void seederInsertedTypes() {
        assertTrue(typeDao.findByCode("YOGA").isPresent());
        assertTrue(typeDao.findByCode("CARDIO").isPresent());
    }
}
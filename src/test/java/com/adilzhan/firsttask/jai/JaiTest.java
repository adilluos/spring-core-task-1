package com.adilzhan.firsttask.jai;

import com.adilzhan.firsttask.dao.hibernateDao.TraineeDao;
import com.adilzhan.firsttask.model.Trainee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest(classes = com.adilzhan.firsttask.FirsttaskApplication.class)
@DataJpaTest
@Testcontainers
public class JaiTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url", postgres::getJdbcUrl);
        r.add("spring.datasource.username", postgres::getUsername);
        r.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    TraineeDao traineeDao;

    @Test
    public void saving() {
        traineeDao.save(new Trainee(UUID.randomUUID().toString(), "Bek", "Zhan", "Bek.Zhan", "12345", true, LocalDate.of(2000,1,1), "Abay"));


        verify(traineeDao).save(any(Trainee.class));

//        System.out.println("hehehehhe");
//        when(traineeDao.findAll()).thenReturn(List.of(new Trainee("1", "a", "a", "a", "a", true, LocalDate.of(2000,1,1), "c")));
//
//        System.out.println(traineeDao.findAll());
    }

}

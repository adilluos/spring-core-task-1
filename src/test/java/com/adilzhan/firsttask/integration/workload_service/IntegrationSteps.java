package com.adilzhan.firsttask.integration.workload_service;

import com.adilzhan.firsttask.client.WorkloadClient;
import com.adilzhan.firsttask.dto.CreateTrainingRequest;
import com.adilzhan.firsttask.dto.WorkloadUpdateRequest;
import com.adilzhan.firsttask.service.web.TrainingService;
import java.util.Collections;

import com.adilzhan.firsttask.service.web.security.ServiceTokenProvider;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(properties = {
        // Fake JWT config just for tests, so JwtService / JwtAuthFilter don't crash
        "security.jwt.secret=test-secret-for-integration-tests-1234567890",
        "security.jwt.issuer=test-issuer",
        "security.jwt.audience=test-audience"
})
public class IntegrationSteps {

    @Container
    static GenericContainer<?> activeMq =
            new GenericContainer<>("rmohr/activemq:latest")
                    .withExposedPorts(61616);

    @Autowired
    private TestRestTemplate restTemplate;

    @MockitoBean
    private WorkloadClient workloadClient;

    @Autowired
    private TrainingService trainingService;

    @MockitoBean
    private ServiceTokenProvider serviceTokenProvider;

    private String trainerUsername;
    private int year;
    private int month;
    private int beforeWorkload;

    @Given("workload-service is running")
    public void workloadServiceRunning() {
        when(workloadClient.getAllWorkloads()).thenReturn(Collections.emptyMap());
    }

    @When("I create a training for trainer {string}")
    public void createTraining(String username) {
        this.trainerUsername = username;
        this.year = 2025;
        this.month = 11;

        beforeWorkload = 0;

        // TODO: make sure this trainer & trainee actually exist in DB
        // either via registration endpoints or pre-seeded data
        CreateTrainingRequest req = new CreateTrainingRequest(
                trainerUsername,
                "Test",
                "Trainee",
                LocalDate.of(year, month, 1),
                50,
                "intense"
        );

        restTemplate.postForEntity(
                "/api/v1/training/add-training",
                req,
                Void.class
        );

        when(workloadClient.getMonthlyWorkload(trainerUsername, year, month))
                .thenReturn(beforeWorkload + 50);
    }

    @Then("workload-service should have workload increased by {int}")
    public void checkWorkload(int amount) throws InterruptedException {
        // give JMS listener a tiny bit of time to consume the message
        Thread.sleep(1000);

        int after = workloadClient.getMonthlyWorkload(trainerUsername, year, month);
        assertEquals(beforeWorkload + amount, after);
    }
}

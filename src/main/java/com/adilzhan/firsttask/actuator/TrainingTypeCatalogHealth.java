package com.adilzhan.firsttask.actuator;

import com.adilzhan.firsttask.model.TrainingType;
import com.adilzhan.firsttask.repository.TrainingTypeRepository;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TrainingTypeCatalogHealth implements HealthIndicator {

    private static final Set<String> REQUIRED = Set.of("YOGA", "CARDIO", "STRENGTH", "CROSSFIT", "PILATES", "BODYBUILDING", "FUNCTIONAL");

    private final TrainingTypeRepository trainingTypeRepository;

    public TrainingTypeCatalogHealth(TrainingTypeRepository trainingTypeRepository) {
        this.trainingTypeRepository = trainingTypeRepository;
    }

    @Override
    public Health health() {
        Set<String> existing = trainingTypeRepository.findAll().stream()
                .map(TrainingType::getCode)
                .collect(Collectors.toSet());

        var missing = REQUIRED.stream()
                .filter(code -> !existing.contains(code))
                .toList();

        return missing.isEmpty()
                ? Health.up().withDetail("trainingType.count", existing.size()).build()
                : Health.down().withDetail("missingTrainingTypes", missing).build();
    }
}

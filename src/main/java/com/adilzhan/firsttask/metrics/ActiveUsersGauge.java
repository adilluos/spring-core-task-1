package com.adilzhan.firsttask.metrics;

import com.adilzhan.firsttask.repository.UserRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class ActiveUsersGauge {
    public ActiveUsersGauge(MeterRegistry meterRegistry, UserRepository userRepository) {
        Gauge.builder("firsttask_active_users", userRepository, UserRepository::countByIsActiveTrue)
                .description("Currently active users")
                .register(meterRegistry);
    }
}

package com.adilzhan.firsttask.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class TrainingMetrics {

    private final MeterRegistry meterRegistry;

    public TrainingMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void incTrainingCreated(String typeCode) {
        Counter.builder("firsttask_training_created_total")
                .description("Number of trainings created")
                .tag("type", typeCode)
                .register(meterRegistry)
                .increment();
    }

    public <T> T timeSearch(String role, Supplier<T> supplier) {
        return Timer.builder("firsttask_training_search_seconds")
                .description("Training search duration")
                .tag("role", role)
                .publishPercentileHistogram()
                .register(meterRegistry)
                .record(supplier);
    }
}

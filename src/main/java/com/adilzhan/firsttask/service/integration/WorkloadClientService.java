package com.adilzhan.firsttask.service.integration;

import com.adilzhan.firsttask.client.WorkloadClient;
import com.adilzhan.firsttask.dto.WorkloadUpdateRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class WorkloadClientService {

    private final WorkloadClient workloadClient;
    private final Queue<WorkloadUpdateRequest> fallbackQueue = new ConcurrentLinkedQueue<>();

    public WorkloadClientService(WorkloadClient workloadClient) {
        this.workloadClient = workloadClient;
    }

    @CircuitBreaker(name = "workloadService", fallbackMethod = "fallbackWorkloadUpdate")
    public void sendWorkloadUpdate(WorkloadUpdateRequest request) {
        workloadClient.sendWorkloadUpdate(request);
        System.out.println("Workload sent to workload-service");
    }

    // Fallback method (called when Feign call fails)
    public void fallbackWorkloadUpdate(WorkloadUpdateRequest request, Throwable ex) {
        System.err.println("workload-service unavailable. Storing request for retry...");
        fallbackQueue.add(request);
    }

    public Queue<WorkloadUpdateRequest> getFallbackQueue() {
        return fallbackQueue;
    }
}
package com.adilzhan.firsttask.scheduler;

import com.adilzhan.firsttask.dto.WorkloadUpdateRequest;
import com.adilzhan.firsttask.service.integration.WorkloadClientService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WorkloadRetryScheduler {

    private final WorkloadClientService workloadClientService;

    public WorkloadRetryScheduler(WorkloadClientService service) {
        this.workloadClientService = service;
    }

    @Scheduled(fixedDelay = 10000)
    public void retryFailedRequests() {
        var queue = workloadClientService.getFallbackQueue();

        while (!queue.isEmpty()) {
            WorkloadUpdateRequest request = queue.peek();
            try {
                workloadClientService.sendWorkloadUpdate(request);
                queue.remove();
                System.out.println("Resent queued workload event");
            } catch (Exception ex) {
                System.err.println("Failed to resend workload event. Will retry later");
                break;
            }
        }
    }
}
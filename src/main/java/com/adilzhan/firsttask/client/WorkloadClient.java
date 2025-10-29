package com.adilzhan.firsttask.client;

import com.adilzhan.firsttask.config.FeignAuthConfig;
import com.adilzhan.firsttask.dto.WorkloadTrainerResponse;
import com.adilzhan.firsttask.dto.WorkloadUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "workload-service", configuration = FeignAuthConfig.class)
public interface WorkloadClient {

    @PostMapping("/api/v1/workload/update")
    void sendWorkloadUpdate(@RequestBody WorkloadUpdateRequest request);

    @GetMapping("/api/v1/workload")
    Map<String, WorkloadTrainerResponse> getAllWorkloads();

    @GetMapping("/api/v1/workload/{username}/{year}/{month}")
    int getMonthlyWorkload(
            @PathVariable String username,
            @PathVariable int year,
            @PathVariable int month
    );
}

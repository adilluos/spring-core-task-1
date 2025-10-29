package com.adilzhan.firsttask.client;

import com.adilzhan.firsttask.config.FeignAuthConfig;
import com.adilzhan.firsttask.dto.WorkloadUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "workload-service", configuration = FeignAuthConfig.class)
public interface WorkloadClient {

    @PostMapping("/api/v1/workload/update")
    void sendWorkloadUpdate(@RequestBody WorkloadUpdateRequest request);
}

package com.adilzhan.firsttask.messaging;

import com.adilzhan.firsttask.dto.WorkloadUpdateRequest;

public interface WorkloadMessageSender {
    void sendWorkloadUpdate(WorkloadUpdateRequest request);
}

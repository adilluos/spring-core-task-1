package com.adilzhan.firsttask.messaging;

import com.adilzhan.firsttask.config.ActiveMqConfig;
import com.adilzhan.firsttask.dto.WorkloadUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class WorkloadMessageProducer {
    private final JmsTemplate jmsTemplate;

    public WorkloadMessageProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendWorkloadUpdate(WorkloadUpdateRequest req) {
        try {
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            String json = mapper.writeValueAsString(req);
            jmsTemplate.convertAndSend(ActiveMqConfig.WORKLOAD_QUEUE, json);
            System.out.println("Sent workload msg: " + json);
        } catch (Exception e) {
            System.err.println("Failed to send JMS message: " + e.getMessage());
        }
    }
}

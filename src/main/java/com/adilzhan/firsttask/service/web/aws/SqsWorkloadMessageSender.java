package com.adilzhan.firsttask.service.web.aws;

import com.adilzhan.firsttask.dto.WorkloadUpdateRequest;
import com.adilzhan.firsttask.messaging.WorkloadMessageSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
@Profile("aws")
public class SqsWorkloadMessageSender implements WorkloadMessageSender {

    private final SqsClient sqsClient;
    private final String queueUrl;

    public SqsWorkloadMessageSender(
            SqsClient sqsClient,
            @Value("${app.sqs.queue-url}") String queueUrl
    ) {
        this.sqsClient = sqsClient;
        this.queueUrl = queueUrl;
    }

    @Override
    public void sendWorkloadUpdate(WorkloadUpdateRequest request) {
        try {
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            String json = mapper.writeValueAsString(request);

            sqsClient.sendMessage(
                    SendMessageRequest.builder()
                            .queueUrl(queueUrl)
                            .messageBody(json)
                            .build()
            );

            System.out.println("Sent SQS workload msg: " + json);
        } catch (Exception e) {
            System.err.println("Failed to send SQS message: " + e.getMessage());
        }
    }
}

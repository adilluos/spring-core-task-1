//package com.adilzhan.firsttask.service.web.aws;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import software.amazon.awssdk.services.sqs.SqsClient;
//import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
//
//@Service
//public class SqsPublisher {
//
//    private final SqsClient sqsClient;
//    private final String queueUrl;
//
//    public SqsPublisher(
//            SqsClient sqsClient,
//            @Value("${app.sqs.queue-url}") String queueUrl
//    ) {
//        this.sqsClient = sqsClient;
//        this.queueUrl = queueUrl;
//    }
//
//    public void send(String message) {
//        sqsClient.sendMessage(
//                SendMessageRequest.builder()
//                        .queueUrl(queueUrl)
//                        .messageBody(message)
//                        .build()
//        );
//    }
//}
//

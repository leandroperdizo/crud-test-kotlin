package com.example.crud.service.impl

import com.example.crud.service.SqsService
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.Message
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest
import software.amazon.awssdk.services.sqs.model.SendMessageRequest

@Service
class SqsServiceImpl(private val sqsClient: SqsClient,
                     private val queueUrl: String) : SqsService {

    override fun sendMessage(message: String) {
        val sendMessageRequest = SendMessageRequest.builder()
            .queueUrl(queueUrl)
            .messageBody(message)
        .build()

        val sendResponse = sqsClient.sendMessage(sendMessageRequest);
    }

    override fun receiveMessage(): List<Message> {
        val receiveMessageRequest = ReceiveMessageRequest.builder()
            .queueUrl(queueUrl)
            .maxNumberOfMessages(10)
            .build()

        val receiveResponse = sqsClient.receiveMessage(receiveMessageRequest);

        return receiveResponse.messages();
    }
}
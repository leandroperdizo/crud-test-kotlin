package com.example.crud.domain.usecase.impl

import com.example.crud.domain.usecase.SqsUseCase
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.Message
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest
import software.amazon.awssdk.services.sqs.model.SendMessageRequest

@Service
class SqsUseCaseImpl(private val sqsClient: SqsClient,
                     private val queueUrl: String) : SqsUseCase {

    override fun sendMessage(message: String) {
        val sendMessageRequest = SendMessageRequest.builder()
            .queueUrl(queueUrl)
            .messageBody(message)
        .build()

        sqsClient.sendMessage(sendMessageRequest);
    }

    override fun receiveMessage(): List<Message> {
        val receiveMessageRequest = ReceiveMessageRequest.builder()
            .queueUrl(queueUrl)
            .maxNumberOfMessages(10)
            .waitTimeSeconds(5)
            .build()

        val receiveResponse = sqsClient.receiveMessage(receiveMessageRequest);

        return receiveResponse.messages();
    }
}
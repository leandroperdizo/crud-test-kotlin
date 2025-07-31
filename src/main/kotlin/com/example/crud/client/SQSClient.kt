package com.example.crud.client

import org.springframework.beans.factory.annotation.Value
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.Message
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest
import software.amazon.awssdk.services.sqs.model.SendMessageRequest

class SQSClient(private val sqsClient: SqsClient,
                @Value("\${aws.sqs.queue-url}") private val queueUrl: String) {

    fun send(message: String) {
        val request = SendMessageRequest.builder()
            .queueUrl(queueUrl)
            .messageBody(message)
            .build()

        sqsClient.sendMessage(request)
    }

    fun receive(): List<Message> {
        val request = ReceiveMessageRequest.builder()
            .queueUrl(queueUrl)
            .maxNumberOfMessages(10)
            .waitTimeSeconds(5)
            .build()

        return sqsClient.receiveMessage(request).messages()
    }
}
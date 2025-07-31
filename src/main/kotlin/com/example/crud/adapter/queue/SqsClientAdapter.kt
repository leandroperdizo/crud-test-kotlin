package com.example.crud.adapter.queue

import com.example.crud.domain.port.MessagingPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.Message
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest
import software.amazon.awssdk.services.sqs.model.SendMessageRequest

@Component
@Primary
class SqsClientAdapter(private val sqsClient: SqsClient,
                       @Value("\${sqs.queue.url}") private val queueUrl: String
) : MessagingPort {

    override fun send(message: String) {
        val request = SendMessageRequest.builder()
            .queueUrl(queueUrl)
            .messageBody(message)
            .build()
        sqsClient.sendMessage(request)
    }

    override fun receive(): List<Message> {
        val request = ReceiveMessageRequest.builder()
            .queueUrl(queueUrl)
            .maxNumberOfMessages(10)
            .waitTimeSeconds(5)
            .build()

        return sqsClient.receiveMessage(request).messages()
    }
}
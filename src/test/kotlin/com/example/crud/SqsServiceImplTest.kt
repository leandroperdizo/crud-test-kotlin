package com.example.crud

import com.example.crud.domaincore.service.impl.SqsServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.*

class SqsServiceImplTest {

    private val sqsClient: SqsClient = mock(SqsClient::class.java)
    private val queueUrl = "https://sqs.us-east-1.amazonaws.com/123456789012/my-queue"
    private val sqsService = SqsServiceImpl(sqsClient, queueUrl)

    @Test
    fun `sendMessage should send a message to SQS`() {
        val messageBody = "Hello, SQS!"
        val sendMessageResponse = SendMessageResponse.builder().messageId("123").build()

        `when`(sqsClient.sendMessage(any(SendMessageRequest::class.java))).thenReturn(sendMessageResponse)

        sqsService.sendMessage(messageBody)

        verify(sqsClient).sendMessage(
            SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .build()
        )
    }

    @Test
    fun `receiveMessage should return a list of messages from SQS`() {
        val message1 = Message.builder().messageId("1").body("Message 1").build()
        val message2 = Message.builder().messageId("2").body("Message 2").build()
        val receiveMessageResponse = ReceiveMessageResponse.builder()
            .messages(message1, message2)
            .build()

        `when`(sqsClient.receiveMessage(any(ReceiveMessageRequest::class.java))).thenReturn(receiveMessageResponse)

        val messages = sqsService.receiveMessage()

        verify(sqsClient).receiveMessage(
            ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(10)
                .waitTimeSeconds(5)
                .build()
        )

        assertEquals(2, messages.size)
        assertEquals("Message 1", messages[0].body())
        assertEquals("Message 2", messages[1].body())
    }
}
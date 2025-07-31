package com.example.crud

import com.example.crud.adapter.queue.SqsClientAdapter
import com.example.crud.domain.usecase.impl.SqsUseCaseImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import software.amazon.awssdk.services.sqs.model.Message
import kotlin.test.assertEquals

class SqsUseCaseImplTest {

    private lateinit var sqsClientAdapter: SqsClientAdapter
    private lateinit var sqsUseCase: SqsUseCaseImpl
    private val queueUrl = "https://sqs.us-east-1.amazonaws.com/123456789012/my-queue"

    @BeforeEach
    fun setUp() {
        sqsClientAdapter = mock(SqsClientAdapter::class.java)
        sqsUseCase = SqsUseCaseImpl(sqsClientAdapter, queueUrl)
    }

    @Test
    fun `sendMessage should delegate call to SqsClientAdapter`() {
        val message = "Hello from test"

        sqsUseCase.sendMessage(message)

        verify(sqsClientAdapter, times(1)).send(message)
    }

    @Test
    fun `receiveMessage should return messages from SqsClientAdapter`() {
        val mockMessage = mock(Message::class.java)
        val messages = listOf(mockMessage)

        `when`(sqsClientAdapter.receive()).thenReturn(messages)

        val result = sqsUseCase.receiveMessage()

        assertEquals(messages, result)
        verify(sqsClientAdapter, times(1)).receive()
    }
}
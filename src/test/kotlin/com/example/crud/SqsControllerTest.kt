package com.example.crud

import com.example.crud.adapter.web.controller.SqsController
import com.example.crud.adapter.web.dto.response.MessageResponse
import com.example.crud.domain.usecase.SqsUseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import software.amazon.awssdk.services.sqs.model.Message

class SqsControllerTest {

    private val sqsUseCase: SqsUseCase = mock(SqsUseCase::class.java)
    private val controller = SqsController(sqsUseCase)

    @Test
    fun `findAllSqsMessages should return mapped messages`() {

        val message1 = mock(Message::class.java)
        `when`(message1.messageId()).thenReturn("1")
        `when`(message1.body()).thenReturn("Hello")

        val message2 = mock(Message::class.java)
        `when`(message2.messageId()).thenReturn("2")
        `when`(message2.body()).thenReturn("World")

        val messageList = listOf(message1, message2)

        `when`(sqsUseCase.receiveMessage()).thenReturn(messageList)

        val result: List<MessageResponse>? = controller.findAllSqsMessages()

        assertEquals(2, result?.size)
        assertEquals("1", result?.get(0)?.messageId)
        assertEquals("Hello", result?.get(0)?.body)
        assertEquals("2", result?.get(1)?.messageId)
        assertEquals("World", result?.get(1)?.body)

        verify(sqsUseCase, times(1)).receiveMessage()
    }
}
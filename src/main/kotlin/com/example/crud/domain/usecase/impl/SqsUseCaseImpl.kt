package com.example.crud.domain.usecase.impl

import com.example.crud.adapter.queue.SqsClientAdapter
import com.example.crud.domain.port.MessagingPort
import com.example.crud.domain.usecase.SqsUseCase
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.sqs.model.Message

@Service
class SqsUseCaseImpl(private val messagePort: MessagingPort,
                     private val queueUrl: String) : SqsUseCase {

    override fun sendMessage(message: String) = messagePort.send(message)
    override fun receiveMessage(): List<Message> = messagePort.receive()
}
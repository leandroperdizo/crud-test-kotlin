package com.example.crud.domain.usecase

import software.amazon.awssdk.services.sqs.model.Message

interface SqsUseCase {

    fun sendMessage(message: String)

    fun receiveMessage(): List<Message>
}
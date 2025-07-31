package com.example.crud.domain.port

import software.amazon.awssdk.services.sqs.model.Message

interface MessagingPort {
    fun send(message: String)
    fun receive(): List<Message>
}
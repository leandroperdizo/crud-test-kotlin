package com.example.crud.service

import software.amazon.awssdk.services.sqs.model.Message

interface SqsService {

    fun sendMessage(message: String)

    fun receiveMessage(): List<Message>
}
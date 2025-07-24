package com.example.demo2.service

import software.amazon.awssdk.services.sqs.model.Message

interface SqsService {

    fun sendMessage(message: String)

    fun receiveMessage(): List<Message>
}
package com.example.crud.webadapter.controller

import com.example.crud.webadapter.dto.response.MessageResponse
import com.example.crud.domaincore.service.SqsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sqs")
class SqsController(
    private val sqsService: SqsService
) {

    @GetMapping
    fun findAllSqsMessages(): List<MessageResponse>?{
        return sqsService.receiveMessage().map {
            MessageResponse(
                messageId = it.messageId(),
                body = it.body()
            )
        }
    }
}
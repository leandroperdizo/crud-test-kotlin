package com.example.crud.adapter.web.controller

import com.example.crud.adapter.web.dto.response.MessageResponse
import com.example.crud.domain.usecase.SqsUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/queue")
class SqsController(
    private val sqsUseCase: SqsUseCase
) {

    @GetMapping
    fun findAllSqsMessages(): List<MessageResponse>?{
        return sqsUseCase.receiveMessage().map {
            MessageResponse(
                messageId = it.messageId(),
                body = it.body()
            )
        }
    }
}
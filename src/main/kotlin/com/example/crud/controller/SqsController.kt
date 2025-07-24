package com.example.crud.controller

import com.example.crud.service.SqsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import software.amazon.awssdk.services.sqs.model.Message

@RestController
@RequestMapping("/sqs")
class SqsController(
    private val sqsService: SqsService
) {

    @GetMapping
    fun findAllSqsMessages(): List<Message>?{
        return sqsService.receiveMessage();
    }
}
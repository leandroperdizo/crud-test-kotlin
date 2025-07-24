package com.example.crud.controller

import com.example.crud.domain.dto.request.UserRequest
import com.example.crud.domain.dto.response.UserResponse
import com.example.crud.service.SqsService
import com.example.crud.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import software.amazon.awssdk.services.sqs.model.Message
import java.net.URI

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
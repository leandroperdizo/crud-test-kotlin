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
@RequestMapping("/user")
class UserController(
    private val userService: UserService,
    private val sqsService: SqsService
) {

    @PostMapping
    fun save(@RequestBody userRequest: UserRequest): ResponseEntity<UserResponse>{

        val response: UserResponse? = userService.save(userRequest)

        return if (response != null) {

            sqsService.sendMessage(response.toString())

            val location = URI.create("/user/${response.id}")
            ResponseEntity.created(location).body(response)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<UserResponse>>?{

        val users = userService.findAll()

        return if (users != null) {
            ResponseEntity.ok(users)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: Long): ResponseEntity<UserResponse>?{

        val userResponse = userService.findById(id)

        return if (userResponse != null) {
            ResponseEntity.ok(userResponse)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: Long, @RequestBody userRequest: UserRequest): ResponseEntity<UserResponse>?{

        val updatedUser = userService.update(id, userRequest)

        return if (updatedUser != null) {
            ResponseEntity.ok(updatedUser)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable("id") id: Long){
        userService.deleteById(id);
    }
}
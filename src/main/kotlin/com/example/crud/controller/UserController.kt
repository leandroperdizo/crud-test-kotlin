package com.example.crud.controller

import com.example.crud.domain.dto.request.UserRequest
import com.example.crud.domain.dto.response.UserResponse
import com.example.crud.service.SqsService
import com.example.crud.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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
    fun findAll(): List<UserResponse>?{
        return userService.findAll();
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: Long): UserResponse?{
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: Long, @RequestBody userRequest: UserRequest): UserResponse?{
        return userService.update(id, userRequest);
    }

    @DeleteMapping("/id")
    fun delete(@PathVariable("id") id: Long){
        userService.delete(id);
    }
}
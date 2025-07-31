package com.example.crud.adapter.web.controller

import com.example.crud.adapter.web.dto.request.UserRequest
import com.example.crud.adapter.web.dto.response.UserResponse
import com.example.crud.adapter.web.mapper.UserWebMapper
import com.example.crud.domain.model.request.UserRequestDomain
import com.example.crud.domain.model.response.UserResponseDomain
import com.example.crud.domain.usecase.SqsUseCase
import com.example.crud.domain.usecase.UserUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/user")
class UserController(
    private val userUseCase: UserUseCase,
    private val sqsUseCase: SqsUseCase,
    private val userWebMapper: UserWebMapper
) {

    @PostMapping
    fun save(@RequestBody userRequest: UserRequest): ResponseEntity<UserResponse> {
        val userRequestDomain: UserRequestDomain = userWebMapper.dtoToDomain(userRequest)
        val userResponseDomain: UserResponseDomain = userUseCase.save(userRequestDomain)
        val userResponse: UserResponse = userWebMapper.domainToDto(userResponseDomain)

        sqsUseCase.sendMessage(userResponse.toString())

        val location = URI.create("/user/${userResponse.id}")
        return ResponseEntity.created(location).body(userResponse)
    }

    @GetMapping
    fun findAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<List<UserResponse>> {
        val userResponseDomainList = userUseCase.findAll(page, size)
        val userResponseList = userWebMapper.entitiesToDto(userResponseDomainList)

        return if (userResponseList.isNotEmpty()) {
            ResponseEntity.ok(userResponseList)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: Long): ResponseEntity<UserResponse> {
        val userResponseDomain = userUseCase.findById(id)

        return if (userResponseDomain != null) {
            val userResponse = userWebMapper.domainToDto(userResponseDomain)
            ResponseEntity.ok(userResponse)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: Long,
        @RequestBody userRequest: UserRequest
    ): ResponseEntity<UserResponse> {
        val userRequestDomain = userWebMapper.dtoToDomain(userRequest)
        val updatedUserResponseDomain = userUseCase.update(id, userRequestDomain)

        return if (updatedUserResponseDomain != null) {
            val userResponse = userWebMapper.domainToDto(updatedUserResponseDomain)
            ResponseEntity.ok(userResponse)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable("id") id: Long) {
        userUseCase.deleteById(id)
    }
}
package com.example.crud.adapter.web.controller

import com.example.crud.adapter.web.dto.request.UserRequest
import com.example.crud.adapter.web.dto.response.UserResponse
import com.example.crud.domain.usecase.SqsUseCase
import com.example.crud.domain.usecase.UserUseCase
import com.example.crud.domain.model.response.UserResponseDomain
import com.example.crud.adapter.web.mapper.UserWebMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/user")
class UserController(
    private val userUseCase: UserUseCase,
    private val sqsUseCase: SqsUseCase,
    private val userMapper: UserWebMapper
) {

    @PostMapping
    fun save(@RequestBody userRequest: UserRequest): ResponseEntity<UserResponse>{

        val userDomain = userMapper.dtoToDomain(userRequest)

        val user: UserResponseDomain = userUseCase.save(userDomain)

        val response = userMapper.domainToDto(user)

        return if (response != null) {

            sqsUseCase.sendMessage(response.toString())

            val location = URI.create("/user/${response.id}")
            ResponseEntity.created(location).body(response)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    @GetMapping
    fun findAll(@RequestParam(defaultValue = "0") page: Int,
                @RequestParam(defaultValue = "10") size: Int): ResponseEntity<List<UserResponse>>?{

        val users = userUseCase.findAll(page, size)

        val response = userMapper.entitiesToDto(users)

        return if (response != null) {
            ResponseEntity.ok(response)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: Long): ResponseEntity<UserResponse>?{

        val userResponse = userUseCase.findById(id)

        return if (userResponse != null) {
            ResponseEntity.ok(userResponse)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: Long, @RequestBody userRequest: UserRequest): ResponseEntity<UserResponse>?{

        val updatedUser = userUseCase.update(id, userRequest)

        return if (updatedUser != null) {
            ResponseEntity.ok(updatedUser)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable("id") id: Long){
        userUseCase.deleteById(id);
    }
}
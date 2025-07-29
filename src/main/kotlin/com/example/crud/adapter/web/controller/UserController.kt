package com.example.crud.adapter.web.controller

import com.example.crud.adapter.web.dto.request.UserRequest
import com.example.crud.adapter.web.dto.response.UserResponse
import com.example.crud.domain.SqsService
import com.example.crud.domain.UserService
import com.example.crud.domain.entity.response.UserResponseDomain
import com.example.crud.adapter.web.mapper.UserWebMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService,
    private val sqsService: SqsService,
    private val userMapper: UserWebMapper
) {

    @PostMapping
    fun save(@RequestBody userRequest: UserRequest): ResponseEntity<UserResponse>{

        val userDomain = userMapper.dtoToDomain(userRequest)

        val user: UserResponseDomain = userService.save(userDomain)

        val response = userMapper.domainToDto(user)

        return if (response != null) {

            sqsService.sendMessage(response.toString())

            val location = URI.create("/user/${response.id}")
            ResponseEntity.created(location).body(response)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    /*@GetMapping
    fun findAll(@RequestParam(defaultValue = "0") page: Int,
                @RequestParam(defaultValue = "10") size: Int): ResponseEntity<List<UserResponse>>?{

        val users = userService.findAll(page, size)

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
    }*/
}
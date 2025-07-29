package com.example.crud.webadapter.controller

import com.example.crud.webadapter.dto.request.UserRequest
import com.example.crud.webadapter.dto.response.UserResponse
import com.example.crud.domaincore.service.SqsService
import com.example.crud.domaincore.service.UserService
import com.example.crud.domaincore.service.domainEntity.UserDomain
import com.example.crud.webadapter.mapper.UserMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService,
    private val sqsService: SqsService,
    private val userMapper: UserMapper
) {

    @PostMapping
    fun save(@RequestBody userRequest: UserRequest): ResponseEntity<UserResponse>{

        val userDomain = userMapper.dtoToDomain(userRequest)

        val response: UserResponse? = userService.save(userDomain)

        return if (response != null) {

            sqsService.sendMessage(response.toString())

            val location = URI.create("/user/${response.id}")
            ResponseEntity.created(location).body(response)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    @GetMapping
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
    }
}
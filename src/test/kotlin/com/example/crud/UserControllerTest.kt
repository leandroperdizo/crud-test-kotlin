package com.example.crud

import com.example.crud.adapter.web.controller.UserController
import com.example.crud.adapter.web.dto.request.UserRequest
import com.example.crud.adapter.web.dto.response.UserResponse
import com.example.crud.adapter.web.mapper.UserWebMapper
import com.example.crud.domain.model.request.UserRequestDomain
import com.example.crud.domain.model.response.UserResponseDomain
import com.example.crud.domain.usecase.SqsUseCase
import com.example.crud.domain.usecase.UserUseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus

class UserControllerTest {

    private lateinit var userUseCase: UserUseCase
    private lateinit var sqsUseCase: SqsUseCase
    private lateinit var userWebMapper: UserWebMapper
    private lateinit var userController: UserController

    @BeforeEach
    fun setUp() {
        userUseCase = mock(UserUseCase::class.java)
        sqsUseCase = mock(SqsUseCase::class.java)
        userWebMapper = mock(UserWebMapper::class.java)
        userController = UserController(userUseCase, sqsUseCase, userWebMapper)
    }

    @Test
    fun `should save user and return created`() {
        val request = UserRequest(1L, "John", "john@email.com")
        val domainRequest = UserRequestDomain(1L, "John", "john@email.com")
        val domainResponse = UserResponseDomain(1L, "John", "john@email.com")
        val response = UserResponse(1L, "John", "john@email.com")

        `when`(userWebMapper.dtoToDomain(request)).thenReturn(domainRequest)
        `when`(userUseCase.save(domainRequest)).thenReturn(domainResponse)
        `when`(userWebMapper.domainToDto(domainResponse)).thenReturn(response)

        val result = userController.save(request)

        assertEquals(HttpStatus.CREATED, result.statusCode)
        assertEquals(response, result.body)
        verify(sqsUseCase).sendMessage(response.toString())
    }

    @Test
    fun `should return list of users`() {
        val domainList = listOf(UserResponseDomain(1L, "Ana", "ana@email.com"))
        val responseList = listOf(UserResponse(1L, "Ana", "ana@email.com"))

        `when`(userUseCase.findAll(0, 10)).thenReturn(domainList)
        `when`(userWebMapper.entitiesToDto(domainList)).thenReturn(responseList)

        val result = userController.findAll(0, 10)

        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(responseList, result.body)
    }

    @Test
    fun `should return 204 when user list is empty`() {
        `when`(userUseCase.findAll(0, 10)).thenReturn(emptyList())
        `when`(userWebMapper.entitiesToDto(emptyList())).thenReturn(emptyList())

        val result = userController.findAll(0, 10)

        assertEquals(HttpStatus.NO_CONTENT, result.statusCode)
    }

    @Test
    fun `should return user by id`() {
        val domain = UserResponseDomain(1L, "Lucas", "lucas@email.com")
        val response = UserResponse(1L, "Lucas", "lucas@email.com")

        `when`(userUseCase.findById(1L)).thenReturn(domain)
        `when`(userWebMapper.domainToDto(domain)).thenReturn(response)

        val result = userController.findById(1L)

        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(response, result.body)
    }

    @Test
    fun `should return 404 if user not found`() {
        `when`(userUseCase.findById(1L)).thenReturn(null)

        val result = userController.findById(1L)

        assertEquals(HttpStatus.NOT_FOUND, result.statusCode)
    }

    @Test
    fun `should update user`() {
        val request = UserRequest(1L, "Carlos", "carlos@email.com")
        val domainRequest = UserRequestDomain(1L, "Carlos", "carlos@email.com")
        val domainResponse = UserResponseDomain(1L, "Carlos", "carlos@email.com")
        val response = UserResponse(1L, "Carlos", "carlos@email.com")

        `when`(userWebMapper.dtoToDomain(request)).thenReturn(domainRequest)
        `when`(userUseCase.update(1L, domainRequest)).thenReturn(domainResponse)
        `when`(userWebMapper.domainToDto(domainResponse)).thenReturn(response)

        val result = userController.update(1L, request)

        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(response, result.body)
    }

    @Test
    fun `should return 404 if update fails`() {
        val request = UserRequest(1L, "Carlos", "carlos@email.com")
        val domainRequest = UserRequestDomain(1L, "Carlos", "carlos@email.com")

        `when`(userWebMapper.dtoToDomain(request)).thenReturn(domainRequest)
        `when`(userUseCase.update(1L, domainRequest)).thenReturn(null)

        val result = userController.update(1L, request)

        assertEquals(HttpStatus.NOT_FOUND, result.statusCode)
    }

    @Test
    fun `should delete user by id`() {
        doNothing().`when`(userUseCase).deleteById(1L)

        userController.deleteById(1L)

        verify(userUseCase).deleteById(1L)
    }
}
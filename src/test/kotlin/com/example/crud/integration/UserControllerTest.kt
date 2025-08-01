package com.example.crud.integration

import com.example.crud.adapter.web.controller.UserController
import com.example.crud.adapter.web.dto.request.UserRequest
import com.example.crud.adapter.web.mapper.UserWebMapper
import com.example.crud.domain.model.request.UserRequestDomain
import com.example.crud.domain.model.response.UserResponseDomain
import com.example.crud.domain.usecase.SqsUseCase
import com.example.crud.domain.usecase.UserUseCase
import io.restassured.RestAssured.given
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.hamcrest.core.IsEqual.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.http.MediaType

class UserControllerTest {

    private val userUseCase = mock(UserUseCase::class.java)
    private val sqsUseCase = mock(SqsUseCase::class.java)
    private val userWebMapper = mock(UserWebMapper::class.java)

    private lateinit var controller: UserController

    @BeforeEach
    fun setup() {
        controller = UserController(userUseCase, sqsUseCase, userWebMapper)
        RestAssuredMockMvc.standaloneSetup(controller)
    }

    @Test
    fun `deve criar um novo usuário com sucesso`() {
        val userRequest = UserRequest(1L, "João", "joao@email.com")
        val userRequestDomain = UserRequestDomain(1L, "João", "joao@email.com")
        val userResponseDomain = UserResponseDomain(1L, "João", "joao@email.com")
        val userResponse = com.example.crud.adapter.web.dto.response.UserResponse(1L, "João", "joao@email.com")

        // Mocks
        `when`(userWebMapper.dtoToDomain(userRequest)).thenReturn(userRequestDomain)
        `when`(userUseCase.save(userRequestDomain)).thenReturn(userResponseDomain)
        `when`(userWebMapper.domainToDto(userResponseDomain)).thenReturn(userResponse)

        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(userRequest)
            .`when`()
            .post("/user")
            .then()
            .statusCode(201)
            .header("Location", "/user/1")
            .body("id", equalTo(1))
            .body("name", equalTo("João"))
            .body("email", equalTo("joao@email.com"))
    }
}
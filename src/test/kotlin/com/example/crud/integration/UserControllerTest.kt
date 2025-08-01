package com.example.crud.integration

import com.example.crud.adapter.web.dto.request.UserRequest
import com.example.crud.adapter.web.dto.response.UserResponse
import com.example.crud.adapter.web.mapper.UserWebMapper
import com.example.crud.domain.model.request.UserRequestDomain
import com.example.crud.domain.model.response.UserResponseDomain
import com.example.crud.domain.usecase.SqsUseCase
import com.example.crud.domain.usecase.UserUseCase
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(UserControllerRestAssuredTest.TestMockConfig::class)
class UserControllerRestAssuredTest {

    @LocalServerPort
    var port: Int = 0

    @BeforeEach
    fun setup() {
        RestAssured.port = port
    }

    @Test
    fun `should return 201 on user creation`() {
        val userRequest = UserRequest(1L, "Teste", "teste@teste.com")
        val userRequestDomain = UserRequestDomain(1L, "Teste", "teste@teste.com")
        val userResponseDomain = UserResponseDomain(1L, "Teste", "teste@teste.com")
        val userResponse = UserResponse(1L, "Teste", "teste@teste.com")

        `when`(TestMockConfig.userWebMapper.dtoToDomain(userRequest)).thenReturn(userRequestDomain)
        `when`(TestMockConfig.userUseCase.save(userRequestDomain)).thenReturn(userResponseDomain)
        `when`(TestMockConfig.userWebMapper.domainToDto(userResponseDomain)).thenReturn(userResponse)

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(userRequest)
            .post("/user")
            .then()
            .statusCode(201)
            .header("Location", "/user/1")
            .body("id", equalTo(1))
            .body("firstName", equalTo("Teste"))
            .body("lastName", equalTo("Teste"))
            .body("email", equalTo("teste@teste.com"))
    }

    @TestConfiguration
    class TestMockConfig {

        companion object {
            val userUseCase: UserUseCase = mock(UserUseCase::class.java)
            val sqsUseCase: SqsUseCase = mock(SqsUseCase::class.java)
            val userWebMapper: UserWebMapper = mock(UserWebMapper::class.java)
        }

        @Bean
        fun userUseCase(): UserUseCase = userUseCase

        @Bean
        fun sqsUseCase(): SqsUseCase = sqsUseCase

        @Bean
        fun userWebMapper(): UserWebMapper = userWebMapper
    }
}

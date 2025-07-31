package com.example.crud

import com.example.crud.domain.model.request.UserRequestDomain
import com.example.crud.domain.model.response.UserResponseDomain
import com.example.crud.domain.port.UserRepository
import com.example.crud.domain.usecase.impl.UserUseCaseImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UserUseCaseImplTest {

    private lateinit var userRepository: UserRepository
    private lateinit var userUseCase: UserUseCaseImpl

    @BeforeEach
    fun setup() {
        userRepository = mock(UserRepository::class.java)
        userUseCase = UserUseCaseImpl(userRepository)
    }

    @Test
    fun `save should return saved user`() {
        val request = UserRequestDomain(id = 1L, name = "Leandro", email = "leandro@example.com")
        val expected = UserResponseDomain(id = 1L, name = "Leandro", email = "leandro@example.com")

        `when`(userRepository.save(request)).thenReturn(expected)

        val result = userUseCase.save(request)

        assertEquals(expected, result)
    }

    @Test
    fun `findAll should return list of users when page has content`() {
        val users = listOf(
            UserResponseDomain(1L, "A", "a@example.com"),
            UserResponseDomain(2L, "B", "b@example.com")
        )
        val pageable = PageRequest.of(0, 10)
        val page: Page<UserResponseDomain> = PageImpl(users, pageable, users.size.toLong())

        `when`(userRepository.findAll(pageable)).thenReturn(page)

        val result = userUseCase.findAll(0, 10)

        assertEquals(users, result)
    }

    @Test
    fun `findAll should return null when page is empty`() {
        val pageable = PageRequest.of(0, 10)
        val emptyPage: Page<UserResponseDomain> = PageImpl(emptyList(), pageable, 0)

        `when`(userRepository.findAll(pageable)).thenReturn(emptyPage)

        val result = userUseCase.findAll(0, 10)

        assertNull(result)
    }

    @Test
    fun `findById should return user if exists`() {
        val expected = UserResponseDomain(1L, "Carlos", "carlos@example.com")
        `when`(userRepository.findById(1L)).thenReturn(expected)

        val result = userUseCase.findById(1L)

        assertEquals(expected, result)
    }

    @Test
    fun `findById should return null if not found`() {
        `when`(userRepository.findById(999L)).thenReturn(null)

        val result = userUseCase.findById(999L)

        assertNull(result)
    }

    @Test
    fun `update should return updated user`() {
        val request = UserRequestDomain(id = 1L, "Updated", "updated@example.com")
        val expected = UserResponseDomain(1L, "Updated", "updated@example.com")

        `when`(userRepository.update(1L, request)).thenReturn(expected)

        val result = userUseCase.update(1L, request)

        assertEquals(expected, result)
    }

    @Test
    fun `deleteById should call repository delete`() {
        doNothing().`when`(userRepository).deleteById(1L)

        userUseCase.deleteById(1L)

        verify(userRepository, times(1)).deleteById(1L)
    }
}

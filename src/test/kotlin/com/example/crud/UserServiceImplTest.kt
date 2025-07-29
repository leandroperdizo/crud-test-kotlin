package com.example.crud

import com.example.crud.adapter.web.dto.request.UserRequest
import com.example.crud.adapter.web.dto.response.UserResponse
import com.example.crud.adapter.resource.db.entity.UserEntity
import com.example.crud.adapter.web.mapper.UserWebMapper
import com.example.crud.adapter.resource.db.repository.UserRepository
import com.example.crud.domain.impl.UserServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

class UserServiceImplTest {

    private val userRepository: UserRepository = mock(UserRepository::class.java)
    private val userMapper: UserWebMapper = mock(UserWebMapper::class.java)
    private val userService = UserServiceImpl(userRepository, userMapper)

    @Test
    fun `save should return UserResponse when user is saved`() {
        val userRequest = UserRequest(id = 1, name = "Alice", email = "alice@example.com")
        val userEntity = UserEntity(id = 1, name = "Alice", email = "alice@example.com")
        val userResponse = UserResponse(id = 1, name = "Alice", email = "alice@example.com")

        `when`(userMapper.dtoToEntity(userRequest)).thenReturn(userEntity)
        `when`(userRepository.save(userEntity)).thenReturn(userEntity)
        `when`(userMapper.entityToDto(userEntity)).thenReturn(userResponse)

        val result = userService.save(userRequest)

        verify(userMapper).dtoToEntity(userRequest)
        verify(userRepository).save(userEntity)
        verify(userMapper).entityToDto(userEntity)
        assertEquals(userResponse, result)
    }

    @Test
    fun `findAll should return a list of UserResponse when users are found`() {
        val userEntity1 = UserEntity(id = 1, name = "Alice", email = "alice@example.com")
        val userEntity2 = UserEntity(id = 2, name = "Bob", email = "bob@example.com")
        val userResponse1 = UserResponse(id = 1, name = "Alice", email = "alice@example.com")
        val userResponse2 = UserResponse(id = 2, name = "Bob", email = "bob@example.com")

        val userPage: Page<UserEntity> = mock(Page::class.java) as Page<UserEntity>
        `when`(userPage.hasContent()).thenReturn(true)
        `when`(userPage.content).thenReturn(listOf(userEntity1, userEntity2))

        `when`(userRepository.findAll(any<Pageable>())).thenReturn(userPage)
        `when`(userMapper.entitiesToDto(listOf(userEntity1, userEntity2))).thenReturn(listOf(userResponse1, userResponse2))

        val result = userService.findAll(0, 10)

        verify(userRepository).findAll(any<Pageable>())
        verify(userMapper).entitiesToDto(listOf(userEntity1, userEntity2))
        assertEquals(2, result?.size)
        assertEquals(userResponse1, result?.get(0))
        assertEquals(userResponse2, result?.get(1))
    }

    @Test
    fun `findById should return UserResponse when user is found`() {
        val userEntity = UserEntity(id = 1, name = "Alice", email = "alice@example.com")
        val userResponse = UserResponse(id = 1, name = "Alice", email = "alice@example.com")

        `when`(userRepository.findById(1)).thenReturn(Optional.of(userEntity))
        `when`(userMapper.entityToDto(userEntity)).thenReturn(userResponse)

        val result = userService.findById(1)

        verify(userRepository).findById(1)
        verify(userMapper).entityToDto(userEntity)
        assertEquals(userResponse, result)
    }

    @Test
    fun `findById should return null when user is not found`() {
        `when`(userRepository.findById(1)).thenReturn(Optional.empty())

        val result = userService.findById(1)

        verify(userRepository).findById(1)
        assertNull(result)
    }

    @Test
    fun `update should return UserResponse when user is updated`() {
        val userRequest = UserRequest(id = 1, name = "Alice Updated", email = "alice.updated@example.com")
        val userEntity = UserEntity(id = 1, name = "Alice", email = "alice@example.com")
        val updatedUserEntity = userEntity.copy(name = userRequest.name, email = userRequest.email)
        val userResponse = UserResponse(id = 1, name = "Alice Updated", email = "alice.updated@example.com")

        `when`(userRepository.findById(1)).thenReturn(Optional.of(userEntity))
        `when`(userRepository.save(updatedUserEntity)).thenReturn(updatedUserEntity)
        `when`(userMapper.entityToDto(updatedUserEntity)).thenReturn(userResponse)

        val result = userService.update(1, userRequest)

        verify(userRepository).findById(1)
        verify(userRepository).save(updatedUserEntity)
        verify(userMapper).entityToDto(updatedUserEntity)
        assertEquals(userResponse, result)
    }

    @Test
    fun `update should return null when user is not found`() {
        val userRequest = UserRequest(id = 1, name = "Alice Updated", email = "alice.updated@example.com")

        `when`(userRepository.findById(1)).thenReturn(Optional.empty())

        val result = userService.update(1, userRequest)

        verify(userRepository).findById(1)
        assertNull(result)
    }

    @Test
    fun `deleteById should call delete on userRepository`() {
        userService.deleteById(1)

        verify(userRepository).deleteById(1)
    }
}
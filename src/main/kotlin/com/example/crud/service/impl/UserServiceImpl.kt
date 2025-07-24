package com.example.crud.service.impl

import com.example.crud.domain.dto.request.UserRequest
import com.example.crud.domain.dto.response.UserResponse
import com.example.crud.entity.UserEntity
import com.example.crud.mapper.UserMapper
import com.example.crud.repository.UserRepository
import com.example.crud.service.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository, private val userMapper: UserMapper) : UserService {
    override fun save(userRequest: UserRequest): UserResponse {

        val user = userMapper.dtoToEntity(userRequest);

        return userMapper.entityToDto(userRepository.save(user));
    }

    override fun findAll(page: Int, size: Int): List<UserResponse>? {

        val pageable = PageRequest.of(page, size)
        val userPage: Page<UserEntity> = userRepository.findAll(pageable)

        return if (userPage.hasContent()) {
            userMapper.entitiesToDto(userPage.content)
        } else {
            null
        }
    }

    override fun findById(id: Long): UserResponse? {
        val user = userRepository.findById(id);

        return if(!user.isEmpty) {
            userMapper.entityToDto(user.get());
        }else{
            null;
        }
    }

    override fun update(
        id: Long,
        userRequest: UserRequest
    ): UserResponse? {

        val user = userRepository.findById(id)

        if (user.isEmpty) {
            return null
        }

        val updatedUser = user.get().copy(
            name = userRequest.name,
            email = userRequest.email
        )

        val savedUser = userRepository.save(updatedUser)

        return userMapper.entityToDto(savedUser)
    }

    override fun deleteById(id: Long) {
        userRepository.deleteById(id)
    }
}
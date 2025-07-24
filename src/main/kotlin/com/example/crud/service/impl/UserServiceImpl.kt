package com.example.crud.service.impl

import com.example.crud.domain.dto.request.UserRequest
import com.example.crud.domain.dto.response.UserResponse
import com.example.crud.mapper.UserMapper
import com.example.crud.repository.UserRepository
import com.example.crud.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository, private val userMapper: UserMapper) : UserService {
    override fun save(userRequest: UserRequest): UserResponse {

        val user = userMapper.dtoToEntity(userRequest);

        return userMapper.entityToDto(userRepository.save(user));
    }

    override fun findAll(): List<UserResponse>? {

        val users = userRepository.findAll();

        return if (!users.isEmpty()){
            userMapper.entitiesToDto(users);
        }
        else{
            null;
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
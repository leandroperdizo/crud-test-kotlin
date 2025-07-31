package com.example.crud.domain.usecase.impl

import com.example.crud.domain.port.UserRepository
import com.example.crud.domain.usecase.UserUseCase
import com.example.crud.domain.model.request.UserRequestDomain
import com.example.crud.domain.model.response.UserResponseDomain
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class UserUseCaseImpl(private val userRepository: UserRepository) : UserUseCase {
    override fun save(userRequestDomain: UserRequestDomain): UserResponseDomain {

        return userRepository.save(userRequestDomain);
    }

    override fun findAll(page: Int, size: Int): List<UserResponseDomain>? {

        val pageable = PageRequest.of(page, size)
        val userPage: Page<UserResponseDomain> = userRepository.findAll(pageable)

        return if (userPage.hasContent()) {
            userPage.content
        } else {
            null
        }
    }

    override fun findById(id: Long): UserResponseDomain? {
        val user = userRepository.findById(id);

        return if(!user.isEmpty) {
            userMapper.entityToDto(user.get());
        }else{
            null;
        }
    }

    override fun update(
        id: Long,
        userRequest: UserRequestDomain
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
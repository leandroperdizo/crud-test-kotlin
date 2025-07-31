package com.example.crud.domain.usecase.impl

import com.example.crud.domain.model.request.UserRequestDomain
import com.example.crud.domain.model.response.UserResponseDomain
import com.example.crud.domain.port.UserRepository
import com.example.crud.domain.usecase.UserUseCase
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
        return userRepository.findById(id)
    }

    override fun update(
        id: Long,
        userRequestDomain: UserRequestDomain
    ): UserResponseDomain? {

        return userRepository.update(id, userRequestDomain)
    }

    override fun deleteById(id: Long) {
        userRepository.deleteById(id)
    }
}
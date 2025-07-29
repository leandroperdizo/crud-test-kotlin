package com.example.crud.domain.impl

import com.example.crud.adapter.resource.UserResource
import com.example.crud.adapter.resource.impl.UserResourceImplDb
import com.example.crud.domain.UserService
import com.example.crud.domain.entity.request.UserRequestDomain
import com.example.crud.domain.entity.response.UserResponseDomain
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userResource: UserResource) : UserService {
    override fun save(userRequestDomain: UserRequestDomain): UserResponseDomain {

        return userResource.save(userRequestDomain);
    }

    /*override fun findAll(page: Int, size: Int): List<UserResponse>? {

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
    }*/
}
package com.example.crud.adapter.resource.impl

import com.example.crud.adapter.resource.UserResource
import com.example.crud.adapter.resource.db.entity.UserEntity
import com.example.crud.adapter.resource.db.repository.UserRepository
import com.example.crud.adapter.resource.mapper.UserResourceMapper
import com.example.crud.domain.entity.request.UserRequestDomain
import com.example.crud.domain.entity.response.UserResponseDomain
import org.springframework.stereotype.Service

@Service
class UserResourceImplDb(private val userRepository: UserRepository,
                         private val userMapper: UserResourceMapper
) : UserResource {

    override fun save(userRequestDomain: UserRequestDomain) : UserResponseDomain {

        val userEntity: UserEntity = userMapper.domainToEntity(userRequestDomain);

        return userMapper.entityToDomain(userRepository.save(userEntity));
    }
}
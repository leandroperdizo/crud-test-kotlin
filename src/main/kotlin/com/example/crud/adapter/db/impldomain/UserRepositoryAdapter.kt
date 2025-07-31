package com.example.crud.adapter.db.impldomain

import com.example.crud.adapter.db.model.UserEntity
import com.example.crud.adapter.db.repository.UserDbAdapterRepository
import com.example.crud.adapter.db.mapper.UserResourceMapper
import com.example.crud.domain.port.UserRepository
import com.example.crud.domain.model.request.UserRequestDomain
import com.example.crud.domain.model.response.UserResponseDomain
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class UserRepositoryAdapter(private val userRepository: UserDbAdapterRepository,
                            private val userMapper: UserResourceMapper
) : UserRepository {

    override fun save(userRequestDomain: UserRequestDomain) : UserResponseDomain {

        val userEntity: UserEntity = userMapper.domainToEntity(userRequestDomain);

        return userMapper.entityToDomain(userRepository.save(userEntity));
    }

    override fun findAll(pageRequest: PageRequest): Page<UserResponseDomain> {

        val usersEntity: Page<UserEntity> = userRepository.findAll(pageRequest);

        val usersResponseDomain: List<UserResponseDomain> = usersEntity.content.map { userMapper.entityToDomain(it) }

        return PageImpl(usersResponseDomain, pageRequest, usersEntity.totalElements)
    }

    override fun findById(id: Long): UserResponseDomain {
        TODO("Not yet implemented")
    }

    override fun update(
        id: Long,
        userRequestDomain: UserRequestDomain
    ): UserResponseDomain {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Long): UserResponseDomain {
        TODO("Not yet implemented")
    }
}
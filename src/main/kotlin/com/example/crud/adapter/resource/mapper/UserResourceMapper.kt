package com.example.crud.adapter.resource.mapper

import com.example.crud.domain.entity.request.UserRequestDomain
import com.example.crud.domain.entity.response.UserResponseDomain
import com.example.crud.adapter.web.dto.response.UserResponse
import com.example.crud.adapter.resource.db.entity.UserEntity
import org.springframework.stereotype.Component

@Component
class UserResourceMapper {

    fun entityToDomain(userEntity: UserEntity) : UserResponseDomain{
        return UserResponseDomain(
            id = userEntity.id,
            name = userEntity.name,
            email = userEntity.email
        );
    }

    fun domainToEntity(userRequestDomain: UserRequestDomain) : UserEntity{
        return UserEntity(
            id = userRequestDomain.id,
            name = userRequestDomain.name,
            email = userRequestDomain.email
        );
    }

    fun entitiesToDomain(userEntities: List<UserEntity>) : List<UserResponseDomain> {
        return userEntities.map { user ->
            UserResponseDomain(
                id = user.id,
                name = user.name,
                email = user.email
            );
        }
    }
}
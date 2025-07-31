package com.example.crud.adapter.db.mapper

import com.example.crud.domain.model.request.UserRequestDomain
import com.example.crud.domain.model.response.UserResponseDomain
import com.example.crud.adapter.db.entity.UserEntity
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
package com.example.crud.webadapter.mapper

import com.example.crud.domaincore.service.domainEntity.UserDomain
import com.example.crud.webadapter.dto.request.UserRequest
import com.example.crud.webadapter.dto.response.UserResponse
import com.example.crud.resourceadapter.entity.UserEntity
import org.springframework.stereotype.Component

@Component
class UserMapper {

    fun dtoToDomain(userRequest: UserRequest) : UserDomain{
        return UserDomain(
            id = userRequest.id,
            name = userRequest.name,
            email = userRequest.email
        );
    }

    fun entityToDto(userEntity: UserEntity) : UserResponse{
        return UserResponse(
            id = userEntity.id,
            name = userEntity.name,
            email = userEntity.email
        );
    }

    fun entitiesToDto(userEntities: List<UserEntity>) : List<UserResponse> {
        return userEntities.map { user ->
            UserResponse(
                id = user.id,
                name = user.name,
                email = user.email
            );
        }
    }
}
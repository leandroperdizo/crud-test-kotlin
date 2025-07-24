package com.example.demo2.mapper

import com.example.demo2.domain.dto.request.UserRequest
import com.example.demo2.domain.dto.response.UserResponse
import com.example.demo2.entity.UserEntity
import org.springframework.stereotype.Component

@Component
class UserMapper {

    fun dtoToEntity(userRequest: UserRequest) : UserEntity{
        return UserEntity(
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
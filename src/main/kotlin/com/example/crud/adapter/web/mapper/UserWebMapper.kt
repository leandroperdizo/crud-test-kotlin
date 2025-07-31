package com.example.crud.adapter.web.mapper

import com.example.crud.domain.model.request.UserRequestDomain
import com.example.crud.domain.model.response.UserResponseDomain
import com.example.crud.adapter.web.dto.request.UserRequest
import com.example.crud.adapter.web.dto.response.UserResponse
import org.springframework.stereotype.Component

@Component
class UserWebMapper {

    fun dtoToDomain(userRequest: UserRequest) : UserRequestDomain{
        return UserRequestDomain(
            id = userRequest.id,
            name = userRequest.name,
            email = userRequest.email
        );
    }

    fun domainToDto(userResponseDomain: UserResponseDomain?) : UserResponse{
        requireNotNull(userResponseDomain) { "userResponseDomain não pode ser null" }
        return UserResponse(
            id = userResponseDomain.id,
            name = userResponseDomain.name,
            email = userResponseDomain.email
        );
    }

    fun entitiesToDto(userResponseDomain: List<UserResponseDomain>?) : List<UserResponse> {
        return userResponseDomain?.map { user ->
            UserResponse(
                id = user.id,
                name = user.name,
                email = user.email
            )
        } ?: emptyList()
    }
}
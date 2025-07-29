package com.example.crud.adapter.resource

import com.example.crud.domain.entity.request.UserRequestDomain
import com.example.crud.domain.entity.response.UserResponseDomain

interface UserResource {

    fun save(userRequestDomain: UserRequestDomain) : UserResponseDomain

}
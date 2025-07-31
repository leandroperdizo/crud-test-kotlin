package com.example.crud.domain.usecase

import com.example.crud.domain.model.request.UserRequestDomain
import com.example.crud.domain.model.response.UserResponseDomain

interface UserUseCase {

    fun save(userRequestDomain: UserRequestDomain) : UserResponseDomain;

    fun findAll(page: Int, size: Int) : List<UserResponseDomain>?;

    fun findById(id: Long) : UserResponseDomain?;

    fun update(id: Long, userRequestDomain: UserRequestDomain) : UserResponseDomain?;

    fun deleteById(id: Long);
}
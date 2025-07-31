package com.example.crud.domain

import com.example.crud.domain.entity.request.UserRequestDomain
import com.example.crud.domain.entity.response.UserResponseDomain

interface UserService {

    fun save(userRequestDomain: UserRequestDomain) : UserResponseDomain;

    fun findAll(page: Int, size: Int) : List<UserResponseDomain>?;

    fun findById(id: Long) : UserResponseDomain?;

    fun update(id: Long, userRequestDomain: UserRequestDomain) : UserResponseDomain?;

    fun deleteById(id: Long);
}
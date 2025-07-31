package com.example.crud.domain

import com.example.crud.domain.entity.request.UserRequestDomain
import com.example.crud.domain.entity.response.UserResponseDomain
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

interface UserRepository {

    fun save(userRequestDomain: UserRequestDomain) : UserResponseDomain

    fun findAll(pageRequest: PageRequest) : Page<UserResponseDomain>

    fun findById(id: Long) : UserResponseDomain

    fun update(id: Long, userRequestDomain: UserRequestDomain) : UserResponseDomain

    fun deleteById(id: Long) : UserResponseDomain

}
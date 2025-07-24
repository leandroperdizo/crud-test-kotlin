package com.example.crud.service

import com.example.crud.domain.dto.request.UserRequest
import com.example.crud.domain.dto.response.UserResponse

interface UserService {

    fun save(userRequest: UserRequest) : UserResponse;

    fun findAll() : List<UserResponse>?;

    fun findById(id: Long) : UserResponse?;

    fun update(id: Long, userRequest: UserRequest) : UserResponse?;

    fun delete(id: Long);
}
package com.example.crud.domaincore.service

import com.example.crud.domaincore.service.domainEntity.UserDomain
import com.example.crud.webadapter.dto.request.UserRequest
import com.example.crud.webadapter.dto.response.UserResponse

interface UserService {

    fun save(userRequest: UserDomain) : UserResponse;

    fun findAll(page: Int, size: Int) : List<UserResponse>?;

    fun findById(id: Long) : UserResponse?;

    fun update(id: Long, userRequest: UserRequest) : UserResponse?;

    fun deleteById(id: Long);
}
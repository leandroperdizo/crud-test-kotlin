package com.example.crud.adapter.resource.db.repository

import com.example.crud.adapter.resource.db.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDbAdapterRepository : JpaRepository<UserEntity, Long> {
}
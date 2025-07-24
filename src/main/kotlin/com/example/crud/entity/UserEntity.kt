package com.example.crud.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "user")
data class UserEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id : Long = 0,
    val name : String = "",
    val email : String = ""
)
package com.pm.runnerz.api_runnerz.dto

import com.pm.runnerz.api_runnerz.models.User

data class UserDto(
    val status: String,
    val message: String,
    val user: List<User>,
    val token: String
)
package com.pm.runnerz.api_runnerz.dao

import com.pm.runnerz.api_runnerz.models.User

data class UserDao(
    val status: String,
    val message: String,
    val user: List<User>,
    val token: String
)
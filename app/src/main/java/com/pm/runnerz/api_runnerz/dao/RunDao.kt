package com.pm.runnerz.api_runnerz.dao

import com.pm.runnerz.api_runnerz.models.Run

data class RunDao(
    val status: String,
    val message: String,
    val run: Run
)
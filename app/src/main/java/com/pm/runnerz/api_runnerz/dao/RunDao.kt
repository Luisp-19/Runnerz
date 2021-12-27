package com.pm.runnerz.api_runnerz.dao

import com.pm.runnerz.api_runnerz.models.Run

data class RunDao(
    val name: String,
    val data: String,
    val duration: String,
    val kms: String,
    val run: Run
)

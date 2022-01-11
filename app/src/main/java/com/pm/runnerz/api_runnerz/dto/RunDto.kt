package com.pm.runnerz.api_runnerz.dto

import com.pm.runnerz.api_runnerz.models.Run

data class RunDto(
    val status: String,
    val message: String,
    val run: Run
)
package com.pm.runnerz.api_runnerz.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Run(
    val id: Int,
    val name: String,
    val data: String,
    val duration: String,
    val kms: String,
    val users_id: Int,
    val created_at: String,
    val user_name: String
) : Parcelable
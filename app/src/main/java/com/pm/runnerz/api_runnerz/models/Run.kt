package com.pm.runnerz.api_runnerz.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Run(
    val id: Int,
    val name_corrida: String,
    val data_corrida: String,
    val duration_corrida: String,
    val kms_corrida: String,
    val users_id: Int,
    val user_name: String
) : Parcelable
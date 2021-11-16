package com.pm.runnerz.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Corrida")
class Running(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    //val duration: String
    //val kms: String
) : Parcelable
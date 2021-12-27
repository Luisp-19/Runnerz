package com.pm.runnerz.api_runnerz.runz

import com.pm.runnerz.api_runnerz.dao.RunDao
import com.pm.runnerz.api_runnerz.models.Run
import retrofit2.Call
import retrofit2.http.*

interface RunApi {
    @GET("runnerz/read")
    fun getRunz(@Header("Authorization") token: String): Call<List<Run>>

    @FormUrlEncoded
    @POST("runnerz/create")
    fun createRun(
        @Header("Authorization") token: String,
        @Field("users_id") users_id: String?,
        @Field("name") name: String,
        @Field("data") data: String,
        @Field("duration") duration: String,
        @Field("kms") kms: String
    ): Call<RunDao>

    @FormUrlEncoded
    @POST("runnerz/update")
    fun updateRun(
        @Header("Authorization") token: String,
        @Field("id") id: Int,
        @Field("name") name: String,
        @Field("data") data: String,
        @Field("duration") duration: String,
        @Field("kms") kms: String
    ): Call<RunDao>

    @FormUrlEncoded
    @POST("runnerz/delete")
    fun deleteRun(
        @Header("Authorization") token: String,
        @Field("id") id: Int
    ): RunDao
}
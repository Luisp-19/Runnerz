package com.pm.runnerz.api_runnerz.runz

import com.pm.runnerz.api_runnerz.dto.RunDto
import com.pm.runnerz.api_runnerz.models.Run
import retrofit2.Call
import retrofit2.http.*

interface RunApi {
    @GET("runnerz/read")
    fun getRunz(
        @Header("Authorization")
        token: String
    ): Call<List<Run>>

    @FormUrlEncoded
    @POST("runnerz/create")
    fun createRun(
        @Header("Authorization") token: String,
        @Field("users_id") users_id: String?,
        @Field("name_corrida") name_corrida: String,
        @Field("data_corrida") data_corrida: String,
        @Field("duration_corrida") duration_corrida: String,
        @Field("kms_corrida") kms_corrida: String
    ): Call<RunDto>

    @FormUrlEncoded
    @POST("runnerz/update")
    fun updateRun(
        @Header("Authorization") token: String,
        @Field("id") id: Int,
        @Field("name_corrida") name_corrida: String,
        @Field("data_corrida") data_corrida: String,
        @Field("duration_corrida") duration_corrida: String,
        @Field("kms_corrida") kms_corrida: String
    ): Call<RunDto>

    @FormUrlEncoded
    @POST("runnerz/delete")
    fun deleteRun(
        @Header("Authorization") token: String,
        @Field("id") id: Int
    ): Call<RunDto>
}
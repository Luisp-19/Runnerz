package com.pm.runnerz.api_runnerz.runz

import com.pm.runnerz.api_runnerz.dto.UserDto
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UsersApi {

    @FormUrlEncoded
    @POST(("users/signin"))
    fun signin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<UserDto>
}
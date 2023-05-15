package com.example.loginapidemo.Api

import com.example.loginapidemo.ModelClass.UserRequest
import com.example.loginapidemo.ModelClass.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

//http://192.168.32.65:4005/api/v1/users/login

interface LoginApi {

    @POST("users/login")
    fun sendData(
        @Body userRequest: UserRequest
    ) : Call<UserResponse>

}
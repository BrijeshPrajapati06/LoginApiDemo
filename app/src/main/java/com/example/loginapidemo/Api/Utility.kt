package com.example.loginapidemo.Api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Utility {

    fun getUser(): Retrofit {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)


        var client: OkHttpClient = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization",
                    "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
                .build()
            return@Interceptor  chain.proceed(newRequest)
        }).addInterceptor(interceptor).build()



        return Retrofit.Builder()
            .baseUrl("http://192.168.32.65:4005/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    }

}
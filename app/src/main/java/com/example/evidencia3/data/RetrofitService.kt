package com.example.evidencia3.data

import com.example.evidencia3.data.validate_user.LoginRequest
import com.example.evidencia3.data.validate_user.ValidateUserResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {
    @POST("RequestLogIn")
    suspend fun getIfUserIsValid(@Body request: LoginRequest): ValidateUserResult
}

object RetrofitServiceFactory {
    fun makeRetrofitServiceFactory(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl("https://aggivyzsf9.execute-api.us-east-1.amazonaws.com/TestStage/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitService::class.java)
    }
}
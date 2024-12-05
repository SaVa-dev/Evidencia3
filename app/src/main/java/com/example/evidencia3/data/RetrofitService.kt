package com.example.evidencia3.data

import com.example.evidencia3.data.get_projects.GetProyectsResult
import com.example.evidencia3.data.get_single_proyect.GetSingleProyectResult
import com.example.evidencia3.data.search_if_user_exists_by_email.GetColaboradorResult
import com.example.evidencia3.data.get_single_proyect.JSONBody as JSONBodySingleProyect
import com.example.evidencia3.data.validate_user.JSONBody as JSONBodyUser
import com.example.evidencia3.data.get_projects.JSONBody as JSONBodyProyects
import com.example.evidencia3.data.search_if_user_exists_by_email.JSONBody as JSONBodySearchUser
import com.example.evidencia3.data.validate_user.ValidateUserResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {
    @POST("RequestLogIn")
    suspend fun getIfUserIsValid(@Body request: JSONBodyUser): ValidateUserResult

    @POST("RequestProjects")
    suspend fun getProyects(@Body request: JSONBodyProyects): GetProyectsResult

    @POST("RequestSingleProyect")
    suspend fun getSingleProyect(@Body request: JSONBodySingleProyect): GetSingleProyectResult

    @POST("SearchIfUserExistsByEmail")
    suspend fun searchIfUserExistsByEmail(@Body request: JSONBodySearchUser): GetColaboradorResult
}

object RetrofitServiceFactory {
    fun makeRetrofitServiceFactory(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl("https://aggivyzsf9.execute-api.us-east-1.amazonaws.com/TestStage/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitService::class.java)
    }
}
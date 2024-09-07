package com.globalmobility.technicaltest.network

import com.globalmobility.technicaltest.model.ObjectRM
import retrofit2.Response
import retrofit2.http.GET

interface EndPoints {

    @GET("character")
    suspend fun getCharacters(): Response<ObjectRM>

}
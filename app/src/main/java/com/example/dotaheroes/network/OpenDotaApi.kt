package com.example.dotaheroes.network

import com.example.dotaheroes.data.Hero
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://api.opendota.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface OpenDotaApiService {

    @GET("api/heroStats")
    //função suspensa pois roda em corrotina, acessos a internet não podem travar a thread principal
    suspend fun getHeroes(): List<Hero>
}

//Cria um instancia da classe OpenDotaApiService
object OpenDotaApi{
    val retrofitService: OpenDotaApiService by lazy{
        retrofit.create(OpenDotaApiService::class.java)
    }
}

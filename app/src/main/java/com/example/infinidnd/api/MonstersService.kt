package com.example.infinidnd.api

import com.example.infinidnd.data.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface MonstersService {
    @GET("/api/monsters/{index}")
    suspend fun searchMonsters(
        @Path("index") type: String
    ) : MonstersDetails

    companion object {
        private const val BASE_URL = "https://www.dnd5eapi.co/api/"
        fun create(): MonstersService {
            val moshi = Moshi.Builder()
                .add(MonstersJsonAdapter())
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
            return retrofit.create(MonstersService::class.java)
        }
    }
}
package com.example.infinidnd.api

import com.example.infinidnd.data.DamageType
import com.example.infinidnd.data.DamageTypeResults
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Path

interface SchoolsService {
    @GET("/api/magic-schools/{index}")
    suspend fun searchDamageTypes(
        @Path("index") type: String
    ) : DamageTypeResults

    companion object {
        private const val BASE_URL = "https://www.dnd5eapi.co/api/"
        fun create(): SchoolsService {
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
            return retrofit.create(SchoolsService::class.java)
        }
    }
}
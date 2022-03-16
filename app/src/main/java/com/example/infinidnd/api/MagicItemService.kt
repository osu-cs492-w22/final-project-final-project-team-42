package com.example.infinidnd.api

import com.example.infinidnd.data.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Path

interface MagicItemService {
    @GET("/api/magic-items/{index}")
    suspend fun searchMagicItems(
        @Path("index") type: String
    ) : MagicItemDetails

    companion object {
        private const val BASE_URL = "https://www.dnd5eapi.co/api/"
        fun create(): MagicItemService {
            val moshi = Moshi.Builder()
                .add(MagicItemJsonAdapter())
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
            return retrofit.create(MagicItemService::class.java)
        }
    }
}
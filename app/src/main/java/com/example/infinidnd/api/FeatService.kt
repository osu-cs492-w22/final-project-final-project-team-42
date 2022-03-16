package com.example.infinidnd.api


import com.example.infinidnd.data.FeatDetails
import com.example.infinidnd.data.FeatListJsonAdapter
import com.example.infinidnd.data.MagicSchoolDetails
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Path

interface FeatService {
    @GET("/api/feats/{index}")
    suspend fun searchFeats(
        @Path("index") type: String
    ) : FeatDetails

    companion object {
        private const val BASE_URL = "https://www.dnd5eapi.co/api/"
        fun create(): FeatService {
            val moshi = Moshi.Builder()
                .add(FeatListJsonAdapter())
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
            return retrofit.create(FeatService::class.java)
        }
    }
}
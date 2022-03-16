package com.example.infinidnd.data


import android.util.Log
import com.example.infinidnd.api.FeatService
import com.example.infinidnd.api.SchoolsService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class FeatRepository(
    private val service: FeatService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadFeatSearch(
        index: String
    ) : Result<FeatDetails> =
        withContext(ioDispatcher) {
            try {
                Log.d("Respository/Received", "${service.searchFeats(index)}")
                val results = service.searchFeats(
                    index
                )
                Log.d("Respository/Received ", "${results}")
                Result.success(results)
            } catch (e: Exception) {
                Log.d("Respository/Received ", "${Result}")
                Result.failure(e)
            }
        }
}
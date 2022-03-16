package com.example.infinidnd.data

import android.util.Log
import com.example.infinidnd.api.ConditionService
import com.example.infinidnd.api.SchoolsService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class ConditionsRepository(
    private val service: ConditionService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadConditionsSearch(
        index: String
    ) : Result<ConditionDetails> =
        withContext(ioDispatcher) {
            try {
                Log.d("Respository/Received result", "${service.searchConditions(index)}")
                val results = service.searchConditions(
                    index
                )
                Log.d("Respository/Received result", "${results}")
                Result.success(results)
            } catch (e: Exception) {
                Log.d("Respository/Received result", "${Result}")
                Result.failure(e)
            }
        }
}
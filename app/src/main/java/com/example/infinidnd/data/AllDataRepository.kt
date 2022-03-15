package com.example.infinidnd.data

import android.util.Log
import com.example.infinidnd.api.AllDataService
import com.example.infinidnd.api.DamageService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class AllDataRepository(
    private val service: AllDataService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadAllData(
        type: String
    ) : Result<List<AllData>> =
        withContext(ioDispatcher) {
            try {
                val results = service.searchAllDamageTypes(
                    type
                )
                Result.success(results.items)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}
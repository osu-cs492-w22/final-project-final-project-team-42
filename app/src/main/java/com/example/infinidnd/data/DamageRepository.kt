package com.example.infinidnd.data

import android.util.Log
import com.example.infinidnd.api.DamageService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class DamageRepository(
    private val service: DamageService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadAllDamage(
        type: String
    ) : Result<List<DamageType>> =
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
    suspend fun loadDamageSearch(
        index: String
    ) : Result<DamageTypeDetails> =
        withContext(ioDispatcher) {
            try {
                Log.d("Respository/Received result", "${service.searchDamageTypes(index)}")
                val results = service.searchDamageTypes(
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
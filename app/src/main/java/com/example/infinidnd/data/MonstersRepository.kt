package com.example.infinidnd.data

import android.util.Log
import com.example.infinidnd.api.DamageService
import com.example.infinidnd.api.EquipmentService
import com.example.infinidnd.api.MonstersService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class MonstersRepository(
    private val service: MonstersService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadMonsterSearch(
        index: String
    ) : Result<MonstersDetails> =
        withContext(ioDispatcher) {
            try {
                Log.d("Respository/Received ", "${service.searchMonsters(index)}")
                val results = service.searchMonsters(
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
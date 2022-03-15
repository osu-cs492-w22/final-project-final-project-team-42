package com.example.infinidnd.data

import android.util.Log
import com.example.infinidnd.api.DamageService
import com.example.infinidnd.api.EquipmentService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class EquipmentRepository(
    private val service: EquipmentService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadEquipmentSearch(
        index: String
    ) : Result<EquipmentDetails> =
        withContext(ioDispatcher) {
            try {
                Log.d("Respository/Received result", "${service.searchEquipment(index)}")
                val results = service.searchEquipment(
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
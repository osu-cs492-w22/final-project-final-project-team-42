package com.example.infinidnd.data

import android.util.Log
import com.example.infinidnd.api.DamageService
import com.example.infinidnd.api.EquipmentService
import com.example.infinidnd.api.MonstersService
import com.example.infinidnd.api.SpellsService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class SpellsRepository(
    private val service: SpellsService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadSpellsSearch(
        index: String
    ) : Result<SpellsDetails> =
        withContext(ioDispatcher) {
            try {
                Log.d("Respository/Received ", "${service.searchSpells(index)}")
                val results = service.searchSpells(
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
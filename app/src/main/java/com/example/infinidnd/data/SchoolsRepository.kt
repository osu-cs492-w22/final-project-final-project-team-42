package com.example.infinidnd.data

import android.util.Log
import com.example.infinidnd.api.SchoolsService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class SchoolsRepository(
    private val service: SchoolsService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadSchoolsSearch(
        index: String
    ) : Result<MagicSchoolDetails> =
        withContext(ioDispatcher) {
            try {
                Log.d("Respository/Received result", "${service.searchMagicSchools(index)}")
                val results = service.searchMagicSchools(
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
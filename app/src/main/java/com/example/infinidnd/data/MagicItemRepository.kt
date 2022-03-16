package com.example.infinidnd.data



import android.util.Log
import com.example.infinidnd.api.FeatService
import com.example.infinidnd.api.MagicItemService
import com.example.infinidnd.api.SchoolsService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class MagicItemRepository(
    private val service: MagicItemService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadMagicItemSearch(
        index: String
    ) : Result<MagicItemDetails> =
        withContext(ioDispatcher) {
            try {
                Log.d("Respository/Received", "${service.searchMagicItems(index)}")
                val results = service.searchMagicItems(
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
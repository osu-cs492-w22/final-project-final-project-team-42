package com.example.infinidnd.ui




import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infinidnd.api.*
import com.example.infinidnd.data.*
import kotlinx.coroutines.launch

class MagicItemsViewModel : ViewModel() {
    private val respository = MagicItemRepository(MagicItemService.create())
    private val alLDataRepository = AllDataRepository(AllDataService.create())

    private val _allTypes = MutableLiveData<List<AllData>>(null)
    val allTypes: LiveData<List<AllData>?> = _allTypes

    private val _searchResults = MutableLiveData<MagicItemDetails>(null)
    val searchResults: LiveData<MagicItemDetails?> = _searchResults

    private val _nameList = MutableLiveData<List<String>>(null)
    val nameList: LiveData<List<String>> = _nameList

    fun loadAllData(
        type: String
    ) {
        viewModelScope.launch {
            val result = alLDataRepository.loadAllData(type)
            _allTypes.value = result.getOrNull()
            var names : List<String> = listOf()
            for (i in _allTypes.value!!){
                names += i.index
            }
            _nameList.value = names
        }
    }

    fun loadSearchResults(
        type: String
    ) {
        viewModelScope.launch {
            Log.d("Viewmodel/Sending", "${type}")
            val result = respository.loadMagicItemSearch(type)

            Log.d("Viewmodel/Received", "${result}")
            _searchResults.value = result.getOrNull()
        }
    }
}
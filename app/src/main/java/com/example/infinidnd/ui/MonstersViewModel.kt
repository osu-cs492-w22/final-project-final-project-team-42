package com.example.infinidnd.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infinidnd.api.AllDataService
import com.example.infinidnd.api.DamageService
import com.example.infinidnd.api.MonstersService
import com.example.infinidnd.data.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MonstersViewModel : ViewModel() {
    private val respository = MonstersRepository(MonstersService.create())
    private val alLDataRepository = AllDataRepository(AllDataService.create())

    private val _allTypes = MutableLiveData<List<AllData>>(null)
    val allTypes: LiveData<List<AllData>?> = _allTypes

    private val _searchResults = MutableLiveData<MonstersDetails>(null)
    val searchResults: LiveData<MonstersDetails?> = _searchResults

    fun loadAllData(
        type: String
    ) {
        viewModelScope.launch {
            val result = alLDataRepository.loadAllData(type)
            _allTypes.value = result.getOrNull()
        }
    }

    fun loadSearchResults(
        type: String
    ) {
        viewModelScope.launch {
            Log.d("Viewmodel/Sending type", "${type}")
            val result = respository.loadMonsterSearch(type)

            Log.d("Viewmodel/Received resu", "${result}")
            _searchResults.value = result.getOrNull()
        }
    }
}
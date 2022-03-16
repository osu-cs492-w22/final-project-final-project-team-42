package com.example.infinidnd.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infinidnd.api.AllDataService
import com.example.infinidnd.api.DamageService
import com.example.infinidnd.api.SpellsService
import com.example.infinidnd.data.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class SpellsViewModel : ViewModel() {
    private val respository = SpellsRepository(SpellsService.create())
    private val alLDataRepository = AllDataRepository(AllDataService.create())

    private val _allTypes = MutableLiveData<List<AllData>>(null)
    val allTypes: LiveData<List<AllData>?> = _allTypes

    private val _searchResults = MutableLiveData<SpellsDetails>(null)
    val searchResults: LiveData<SpellsDetails?> = _searchResults

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
            val result = respository.loadSpellsSearch(type)

            Log.d("Viewmodel/Received result", "${result}")
            _searchResults.value = result.getOrNull()
        }
    }
}
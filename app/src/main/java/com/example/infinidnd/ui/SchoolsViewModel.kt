package com.example.infinidnd.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infinidnd.api.AllDataService
import com.example.infinidnd.api.SchoolsService
import com.example.infinidnd.data.*
import kotlinx.coroutines.launch

class SchoolsViewModel : ViewModel() {
    private val respository = SchoolsRepository(SchoolsService.create())
    private val alLDataRepository = AllDataRepository(AllDataService.create())

    private val _allTypes = MutableLiveData<List<AllData>>(null)
    val allTypes: LiveData<List<AllData>?> = _allTypes

    private val _searchResults = MutableLiveData<MagicSchoolDetails>(null)
    val searchResults: LiveData<MagicSchoolDetails?> = _searchResults

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
            val result = respository.loadSchoolsSearch(type)

            Log.d("Viewmodel/Received result", "${result}")
            _searchResults.value = result.getOrNull()
        }
    }
}
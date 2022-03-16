package com.example.infinidnd.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.githubsearchwithsettings.data.LoadingStatus
import com.example.infinidnd.api.AllDataService
import com.example.infinidnd.api.DamageService
import com.example.infinidnd.data.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class DamageViewModel : ViewModel() {
    private val respository = DamageRepository(DamageService.create())
    private val alLDataRepository = AllDataRepository(AllDataService.create())

    private val _allTypes = MutableLiveData<List<AllData>>(null)
    val allTypes: LiveData<List<AllData>?> = _allTypes

    private val _searchResults = MutableLiveData<DamageTypeDetails>(null)
    val searchResults: LiveData<DamageTypeDetails?> = _searchResults

    private val _nameList = MutableLiveData<List<String>>(null)
    val nameList: LiveData<List<String>> = _nameList

    private val _loadingStatus = MutableLiveData(LoadingStatus.SUCCESS)
    val loadingStatus: LiveData<LoadingStatus> = _loadingStatus

    fun loadAllData(
        type: String
    ) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.LOADING
            val result = alLDataRepository.loadAllData(type)
            _allTypes.value = result.getOrNull()
            var names : List<String> = listOf()
            for (i in _allTypes.value!!){
                names += i.index
            }
            _nameList.value = names
            _loadingStatus.value = when (result.isSuccess) {
                true ->  LoadingStatus.SUCCESS
                false -> LoadingStatus.ERROR
            }
        }
    }

    fun loadSearchResults(
        type: String
    ) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.LOADING
            Log.d("Viewmodel/Sending type", "${type}")
            val result = respository.loadDamageSearch(type)

            Log.d("Viewmodel/Received resu", "${result}")
            _searchResults.value = result.getOrNull()
            _loadingStatus.value = when (result.isSuccess) {
                true ->  LoadingStatus.SUCCESS
                false -> LoadingStatus.ERROR
            }
        }
    }
}
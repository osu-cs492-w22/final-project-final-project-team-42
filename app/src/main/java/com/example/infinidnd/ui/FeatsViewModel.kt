package com.example.infinidnd.ui


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.githubsearchwithsettings.data.LoadingStatus
import com.example.infinidnd.api.AllDataService
import com.example.infinidnd.api.FeatService
import com.example.infinidnd.data.*
import kotlinx.coroutines.launch

class FeatsViewModel : ViewModel() {
    private val respository = FeatRepository(FeatService.create())
    private val alLDataRepository = AllDataRepository(AllDataService.create())

    private val _allTypes = MutableLiveData<List<AllData>>(null)
    val allTypes: LiveData<List<AllData>?> = _allTypes

    private val _searchResults = MutableLiveData<FeatDetails>(null)
    val searchResults: LiveData<FeatDetails?> = _searchResults

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
            val result = respository.loadFeatSearch(type)

            Log.d("Viewmodel/Received", "${result}")
            _searchResults.value = result.getOrNull()
            _loadingStatus.value = when (result.isSuccess) {
                true ->  LoadingStatus.SUCCESS
                false -> LoadingStatus.ERROR
            }
        }
    }
}
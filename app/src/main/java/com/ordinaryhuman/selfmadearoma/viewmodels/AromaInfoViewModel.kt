package com.ordinaryhuman.selfmadearoma.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ordinaryhuman.selfmadearoma.data.aromaOil.AromaOil
import com.ordinaryhuman.selfmadearoma.data.aromaOil.AromaOilRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AromaInfoViewModel @ViewModelInject constructor(
    private val aromaOilRepository: AromaOilRepository
): ViewModel() {

    private val _aromaOilNames = MutableLiveData<List<AromaOil>>()
    val aromaOilNames: LiveData<List<AromaOil>>
    get() = _aromaOilNames

    init {
        getAromaOilNames()
    }

    fun getAromaOilNames() {
        viewModelScope.launch(Dispatchers.IO) {
            val list: List<AromaOil> = aromaOilRepository.getAromaOils()
            _aromaOilNames.postValue(list)
        }
    }

    fun searchAromaOil(newText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val searchResults = aromaOilRepository.findByAromaOilNameLike(newText)

            _aromaOilNames.postValue(searchResults)
        }
    }
}
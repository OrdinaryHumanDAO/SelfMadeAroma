package com.example.selfmadearoma.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.selfmadearoma.data.aromaOil.AromaOil
import com.example.selfmadearoma.data.aromaOil.AromaOilRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AromaInfoDetailViewModel @ViewModelInject constructor(
    private val aromaOilRepository: AromaOilRepository
): ViewModel() {

    private val _aromaOilInfo = MutableLiveData<AromaOil>()
    val aromaOilInfo: LiveData<AromaOil>
        get() = _aromaOilInfo


    fun getAromaOil(AromaOilId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _aromaOilInfo.postValue(aromaOilRepository.getAromaOil(AromaOilId))
        }
    }
}
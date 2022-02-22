package com.ordinaryhuman.selfmadearoma.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ordinaryhuman.selfmadearoma.data.myArom.MyAromaRepository
import com.ordinaryhuman.selfmadearoma.data.myArom.MyAromaWithMyAromaOilInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserAromaDetailViewModel @ViewModelInject constructor(
    private val myAromaRepository: MyAromaRepository
): ViewModel() {

    private var _myAromaInfo = MutableLiveData<MyAromaWithMyAromaOilInfo>()
    val myAromaInfo: LiveData<MyAromaWithMyAromaOilInfo>
    get() = _myAromaInfo

    fun getMyAroma(myAromaId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _myAromaInfo.postValue(myAromaRepository.getMyAroma(myAromaId))
        }
    }

    fun getMyAromaName(): String {
        return myAromaInfo.value!!.myAroma.myAromaName
    }
}
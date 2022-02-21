package com.example.selfmadearoma.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.selfmadearoma.data.myArom.MyAromaOilInfo
import com.example.selfmadearoma.data.myArom.MyAromaOilInfoRepository
import com.example.selfmadearoma.data.myArom.MyAromaRepository
import com.example.selfmadearoma.data.myArom.MyAromaWithMyAromaOilInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UserAromaEditViewModel @ViewModelInject constructor(
    private val myAromaRepository: MyAromaRepository,
    private val myAromaOilInfoRepository: MyAromaOilInfoRepository
): ViewModel() {
    var cardViewNum = 0

    var ifFirstFlag = true
    var firstAromaOilInfoSize = 0

    var navigatePopBackFlag = MutableLiveData<Boolean>()

    var _myAromaInfo = MutableLiveData<MyAromaWithMyAromaOilInfo>()
    val myAromaInfo: LiveData<MyAromaWithMyAromaOilInfo>
        get() = _myAromaInfo

    var etMyAromaNameText = ""

    private val deleteMyAromaInfoList: MutableList<MyAromaOilInfo> = mutableListOf()


    fun addCardViewNum() {
        cardViewNum++
    }

    fun initialCardViewNum() {
        cardViewNum = 0
    }

    fun addMyAromaInfoDate() {
        val data = MyAromaOilInfo(0, getMyAromaName(), "アロマを選択してください", 0)
        _myAromaInfo.value!!.myAromaOilInfo.add(data)
    }

    fun deleteCardView(cardViewId: Int) {
        cardViewNum--

        if(firstAromaOilInfoSize > cardViewId) {
            firstAromaOilInfoSize--
            deleteMyAromaInfoList.add(myAromaInfo.value!!.myAromaOilInfo[cardViewId])
        }

        val list = _myAromaInfo.value!!
        list.myAromaOilInfo.removeAt(cardViewId)
        _myAromaInfo.value = list
    }

    fun getMyAroma(myAromaId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _myAromaInfo.postValue(myAromaRepository.getMyAroma(myAromaId))
        }
    }

    fun getMyAromaName(): String {
        return myAromaInfo.value!!.myAroma.myAromaName
    }

    fun getMyAromaOilName(index: Int): String {
        return myAromaInfo.value!!.myAromaOilInfo[index].aromaOilName
    }

    fun getMyAromaOilAmount(index: Int): String {
        return "${myAromaInfo.value!!.myAromaOilInfo[index].aromaOilAmount} ml"
    }

    fun getMyAromaOilInfoSize(): Int {
        return myAromaInfo.value!!.myAromaOilInfo.size
    }

    fun getMyAromaOilInfoIndices(): IntRange {
        return myAromaInfo.value!!.myAromaOilInfo.indices
    }

    fun setFirstAromaOilInfoSize() {
        firstAromaOilInfoSize = getMyAromaOilInfoSize()
        ifFirstFlag = false
    }

    fun setEtMyAromaNameText() {
        etMyAromaNameText = getMyAromaName()
    }

    fun editMyAromaName(str: String) {
        _myAromaInfo.value!!.myAroma.myAromaName = str
        for(i in getMyAromaOilInfoIndices()) {
            _myAromaInfo.value!!.myAromaOilInfo[i].myAromaCreatorName = str
        }
    }

    fun editMyAromaAmount(){
        var total = 0
        for(i in getMyAromaOilInfoIndices()){
            total += myAromaInfo.value!!.myAromaOilInfo[i].aromaOilAmount
        }
        _myAromaInfo.value!!.myAroma.amount = total
    }

    fun editMyAromaOilInfoName(index: Int, str: String) {
        val data = _myAromaInfo.value!!
        data.myAromaOilInfo[index].aromaOilName = str
        _myAromaInfo.value = data
    }

    fun editMyAromaOilInfoAmount(index: Int, str: String) {
        val data = _myAromaInfo.value!!
        data.myAromaOilInfo[index].aromaOilAmount = str.toInt()
        _myAromaInfo.value = data
    }


    fun updateMyAroma() {
        viewModelScope.launch(Dispatchers.IO) {
            editMyAromaAmount()
            //_myAromaInfo.value!!.myAroma.myAromaName = etMyAromaNameText
            myAromaRepository.updateMyAroma(myAromaInfo.value!!.myAroma)
            for(i in getMyAromaOilInfoIndices()) {
                _myAromaInfo.value!!.myAromaOilInfo[i].myAromaCreatorName = getMyAromaName()
                myAromaOilInfoRepository.updateMyAromaOilInfo(myAromaInfo.value!!.myAromaOilInfo[i])
            }
        }
    }

    fun updateMyAromaOilInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            for(i in deleteMyAromaInfoList) {
                myAromaOilInfoRepository.deleteMyAromaOilInfo(i)
            }
            //Log.d("aiueo", "$firstAromaOilInfoSize ${getMyAromaOilInfoIndices()}")
            for(i in getMyAromaOilInfoIndices()) {

                val existingData = firstAromaOilInfoSize >= i+1
                if (existingData) {
                    myAromaOilInfoRepository.updateMyAromaOilInfo(myAromaInfo.value!!.myAromaOilInfo[i])
                }

                val newData = firstAromaOilInfoSize < i+1
                if (newData) {
                    myAromaOilInfoRepository.insertMyAromaOilInfo(myAromaInfo.value!!.myAromaOilInfo[i])
                }
            }
            navigatePopBackFlag.postValue(true)
        }
    }
}